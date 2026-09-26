package com.Security.Authify.service;

import com.Security.Authify.entity.*;
import com.Security.Authify.jwtUtils.AuthUtil;
import com.Security.Authify.jwtUtils.InvitationTokenProvider;
import com.Security.Authify.repository.InvitationAuditLogRepository;
import com.Security.Authify.repository.InvitationRepository;
import com.Security.Authify.repository.RoleRepository;
import com.Security.Authify.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl implements InvitationService {

    @Value("${frontend.url}")
    private String URL;

    private final InvitationRepository invitationRepository;
    private final InvitationAuditLogRepository invitationAuditLogRepository;
    private final EmailService emailService;
    private final InvitationTokenProvider invitationTokenProvider;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;

    //Admin invite user
    @Override
    public void inviteUser(String email, ERole roleName) {
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("User already exists");
        }
        if(invitationRepository.findByEmail(email)
                .filter(i -> i.getStatus() == InvitationStatus.PENDING)
                .isPresent()){
            throw new RuntimeException("User already invited");
        }
        Role existingRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserEntity currentUser = authUtil.getCurrentuser();

        InvitationEntity invitation = new InvitationEntity();
        invitation.setEmail(email);
        invitation.setInvitedRole(existingRole);
        invitation.setInvitedByAdmin(currentUser);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setExpiresAt(LocalDateTime.now().plusHours(24));
        invitation.setCreatedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
        String token = invitationTokenProvider.generateToken(invitation);
        invitation.setToken(token);
        invitationRepository.save(invitation);
        log.info("Invitation details saved");

        logInvitationAction(invitation,"CREATED","Invited by: " + currentUser.getName());

        String setUpUrl = URL + "/setup?token=" + token;
        System.out.println(setUpUrl);
        emailService.invitationEmail(email, setUpUrl, currentUser.getName(), existingRole.getName());
        log.info("Invitation email sent to user");
    }

    //User setup account with token
    @Override
    public UserEntity setupAccountWithToken(String token, String password, String name) {
        Claims claims;
        try{
            claims = invitationTokenProvider.validateAndExtract(token);
        }catch (ExpiredJwtException e){
            throw new RuntimeException("Invitation link is expired");
        } catch (JwtException e){
            throw new RuntimeException("Invalid invitation link");
        }
        if(!"INVITATION".equals(claims.get("type"))){
            throw new RuntimeException("Invalid invitation link");
        }
        Long invitationId = claims.get("invitation_id", Long.class);
         InvitationEntity invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("invitation not found"));

         if(invitation.getStatus() == InvitationStatus.ACCEPTED){
             throw new RuntimeException("User already accepted the invitation");
         }
         if(invitation.getStatus() == InvitationStatus.REVOKED){
             throw new RuntimeException("User already revoked by admin");
         }
         if(invitation.getExpiresAt().isBefore(LocalDateTime.now())){
             throw new RuntimeException("Invitation link has expired");
         }

         String hashedPass = passwordEncoder.encode(password);

         UserEntity user = new UserEntity();
         user.setEmail(invitation.getEmail());
         user.setName(name);
         user.setPassword(hashedPass);
         user.setRoles(Set.of(invitation.getInvitedRole()));
         user.setStatus(AccountStatus.APPROVED);
         userRepository.save(user);

        logInvitationAction(invitation,"ACCEPTED", "Account setup completed by : "+name);

         invitation.setStatus(InvitationStatus.ACCEPTED);
         invitation.setAcceptedAt(LocalDateTime.now());
         invitation.setAcceptedUser(user);
         invitationRepository.save(invitation);
         log.info("Invitation details updated");

//         userService.sendOtpToVerifyEmail(invitation.getEmail());
//         log.info("Invitation Email send successfully to User");

         return user;
    }

    //Admin Resend invitation
    @Override
    public void resendInvitation(Long invitationId) {
        InvitationEntity invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("invitation id not found"));

        if(invitation.getStatus() != InvitationStatus.PENDING){
            throw new RuntimeException("Can Only send pending Invitation");
        }

        String newToken = invitationTokenProvider.generateToken(invitation);
        invitation.setToken(newToken);
        invitation.setExpiresAt(LocalDateTime.now().plusHours(24));
        invitationRepository.save(invitation);
        log.info("Resend Invitation details saved");
        UserEntity currentUser = authUtil.getCurrentuser();
        logInvitationAction(invitation, "RESEND", "Resend by: " + currentUser.getName());
        String setUpUrl = String.format(URL+"/setup?token="+newToken);
        System.out.println(setUpUrl);
        emailService.invitationEmail(invitation.getEmail(), setUpUrl, currentUser.getName(), invitation.getInvitedRole().getName());
        log.info("Invitation email sent to user");
    }

    //Admin Revoke invitation
    @Override
    public void revokeInvitation(Long invitationId) {
        InvitationEntity invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("invitation not found"));
        if(invitation.getStatus() == InvitationStatus.ACCEPTED){
            throw new RuntimeException("Cannot revoke already accepted Invitation");
        }
        if (invitation.getStatus() == InvitationStatus.REVOKED) {
            throw new RuntimeException("Invitation has been revoked");
        }
        invitation.setStatus(InvitationStatus.REVOKED);
        invitation.setExpiresAt(LocalDateTime.now());
        invitationRepository.save(invitation);
        log.info("Invitation revoked successfully");
        UserEntity currentUser = authUtil.getCurrentuser();
        logInvitationAction(invitation, "REVOKED", "Revoked by: " + currentUser.getName());
    }

    //Audit log
    @Override
    public void logInvitationAction(InvitationEntity invitation, String action, String details) {
        InvitationAuditLogEntity log = new InvitationAuditLogEntity();
        log.setInvitation(invitation);
        log.setAction(action);
        log.setDetails(details);
        invitationAuditLogRepository.save(log);
    }
}

package com.Security.Authify.service;

import com.Security.Authify.entity.AccountStatus;
import com.Security.Authify.entity.ERole;
import com.Security.Authify.entity.Role;
import com.Security.Authify.entity.UserEntity;
import com.Security.Authify.io.ProfileRequest;
import com.Security.Authify.io.ProfileResponse;
import com.Security.Authify.io.UserMapper;
import com.Security.Authify.repository.RoleRepository;
import com.Security.Authify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public ProfileResponse createProfile(ProfileRequest request) {
        Role studentRole = roleRepository.findByName(ERole.STUDENT)
                .orElseThrow(() -> new RuntimeException("Student Role not found"));
        UserEntity newProfile = userMapper.convertToUserEntity(request);
        newProfile.setRoles(Set.of(studentRole));
        newProfile.setStatus(AccountStatus.PENDING);
        newProfile = userRepository.save(newProfile);
        return userMapper.convertToProfileResponse(newProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: "+email));
        return userMapper.convertToProfileResponse(existingUser);
    }

    @Override
    @Transactional
    public void sendResetOtp(String email) {
         UserEntity existingEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: "+email));
         //Generate 6 digit otp
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        //calculate expiry time (current time + 10 millisec)
         long expiryTime = System.currentTimeMillis() + (10 * 60 * 1000);
         //update profile/user
        existingEntity.setResetOtp(otp);
        existingEntity.setResetOtpExpiryAt(expiryTime);
        //save into db
        userRepository.save(existingEntity);

        try{
            emailService.sendResetOtpEmail(existingEntity.getEmail(), otp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void resetPassword(String email, String otp, String newPassword) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: "+email));
        if(existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp)){
            throw new RuntimeException("Invalid otp");
        }
        if(existingUser.getResetOtpExpiryAt() < System.currentTimeMillis()){
            throw new RuntimeException("Otp expired");
        }

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setResetOtp(null);
        existingUser.setResetOtpExpiryAt(0L);
        //save into db
        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void sendOtpToVerifyEmail(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: "+email));
        if(existingUser.getIsAccountVerified() != null && existingUser.getIsAccountVerified()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Already Verified");
        }
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
         long expiryTime = System.currentTimeMillis() + (5 * 60 * 60 *1000);

         existingUser.setVerifyOtp(otp);
         existingUser.setVerifyOtpExpiryAt(expiryTime);

         userRepository.save(existingUser);

         try{
             emailService.sendOtpToVerifyEmail(existingUser.getEmail(), otp);
         } catch (Exception e) {
            throw new RuntimeException("Unable to send OTP to verify Email");
         }
    }

    @Override
    @Transactional
    public void verifyEmailOtp(String email, String otp) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found:"+email));
        if(existingUser.getVerifyOtp() == null || !existingUser.getVerifyOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP");
        }
        if(existingUser.getVerifyOtpExpiryAt() < System.currentTimeMillis()){
            throw new RuntimeException("OTP expired");
        }
        existingUser.setIsAccountVerified(true);
        existingUser.setVerifyOtp(null);
        existingUser.setVerifyOtpExpiryAt(0L);

        userRepository.save(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public String getLoggedInUserId(String email) {
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "+email));
        return existingUser.getUserId();
    }


}

package com.Security.Authify.service;

import com.Security.Authify.entity.AccountStatus;
import com.Security.Authify.entity.UserEntity;
import com.Security.Authify.io.ApprovalMapper;
import com.Security.Authify.io.ApprovalResponse;
import com.Security.Authify.io.ProfileResponse;
import com.Security.Authify.io.UserMapper;
import com.Security.Authify.jwtUtils.AuthUtil;
import com.Security.Authify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ApprovalServiceImpl implements ApprovalService{

    private final UserRepository userRepository;
    private final ApprovalMapper approvalMapper;
    private final EmailService emailService;
    private final AuthUtil authUtil;

    @Override
    public List<ApprovalResponse> pendingUsers() {
        List<UserEntity> pendingUsers = userRepository.findByStatus(AccountStatus.PENDING);
        return pendingUsers.stream()
                .map( (user) -> approvalMapper.convertToApprovalResponse(user))
                .toList();
    }

    @Override
    public void approveUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getStatus() != AccountStatus.PENDING){
            log.warn("User approval skipped, userId={}, currentStatus={}", user.getId(), user.getStatus());
            throw new RuntimeException("User status is not pending");
        }
        user.setStatus(AccountStatus.APPROVED);
        userRepository.save(user);
        UserEntity currentUser = authUtil.getCurrentuser();
        log.info("User Account Approved, UserName={}, approvedBy={}", user.getName(), currentUser.getName());
        String email = user.getEmail();
        String name = user.getName();
        emailService.approvalAcceptedEmailMsg(email, name);
    }

    @Override
    public void rejectUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getStatus() != AccountStatus.PENDING){
            log.warn("User approval skipped, userId={}, currentStatus={}", user.getId(), user.getStatus());
            throw new RuntimeException("User is not pending");
        }
        user.setStatus(AccountStatus.REJECTED);
        userRepository.save(user);
        UserEntity currentUser = authUtil.getCurrentuser();
        log.info("User Account Rejected, UserName={}, rejectedBy={}", user.getName(), currentUser.getName());
        String email = user.getEmail();
        String name = user.getName();
        emailService.approvalRejectedEmailMsg(email, name);
    }
}

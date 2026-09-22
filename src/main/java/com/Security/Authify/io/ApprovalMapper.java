package com.Security.Authify.io;

import com.Security.Authify.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ApprovalMapper {

    public ApprovalResponse convertToApprovalResponse(UserEntity user){
        return ApprovalResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }
}

package com.Security.Authify.service;

import com.Security.Authify.entity.UserEntity;
import com.Security.Authify.io.ApprovalResponse;
import com.Security.Authify.io.ProfileResponse;

import java.util.List;

public interface ApprovalService {

    List<ApprovalResponse> pendingUsers();

    void approveUser(Long id);

    void rejectUser(Long id);
}

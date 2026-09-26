package com.Security.Authify.service;

import com.Security.Authify.entity.ERole;
import com.Security.Authify.entity.InvitationEntity;
import com.Security.Authify.entity.UserEntity;

public interface InvitationService {

    public void inviteUser(String email, ERole role);

    public UserEntity setupAccountWithToken(String token, String password, String name);

    public void resendInvitation(Long invitationId);

    public void revokeInvitation(Long invitationId);

    public void logInvitationAction(InvitationEntity invitation, String action,String details);
}

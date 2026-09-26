package com.Security.Authify.controller;

import com.Security.Authify.io.InvitationRequest;
import com.Security.Authify.repository.UserRepository;
import com.Security.Authify.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/invitations")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('INVITE_USER')")
public class AdminInvitationController {

    private final InvitationService invitationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendInvitation(@RequestBody @Valid InvitationRequest req){
        try{
            invitationService.inviteUser(req.getEmail(), req.getRole());
            return ResponseEntity.ok("Invitation sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fail to invite user");
        }
    }

    @PostMapping("/{id}/resend")
    public ResponseEntity<String> resendInvitation(@PathVariable Long id){
        try {
            invitationService.resendInvitation(id);
            return ResponseEntity.ok("Invitation resent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fail to resend invitation :"+ e.getMessage());
        }
    }

    @DeleteMapping("/{id}/revoke")
    public ResponseEntity<String> revokeInvitation(@PathVariable Long id){
        try {
            invitationService.revokeInvitation(id);
            return ResponseEntity.ok("Invitation revoked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fail to revoke invitation");
        }
    }
}

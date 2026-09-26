package com.Security.Authify.controller;

import com.Security.Authify.io.SetupAccountRequest;
import com.Security.Authify.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/invitations")
@RequiredArgsConstructor
public class PublicInvitationController {

    private final InvitationService invitationService;

    //User Setup account with token
    @PostMapping("/setup-account")
    public ResponseEntity<String> setupAccount(@RequestBody @Valid SetupAccountRequest req){
        try {
            invitationService.setupAccountWithToken(req.getToken(), req.getPassword(), req.getName());
            return ResponseEntity.ok("Account setup successfully, You can login now with credentials");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fail to setup account");
        }
    }
}

package com.Security.Authify.controller;

import com.Security.Authify.io.ApprovalResponse;
import com.Security.Authify.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/approval")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;

    @GetMapping("/pending-users")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<ApprovalResponse>> pendingUsers(){
        try {
            List<ApprovalResponse> pendingUsers = approvalService.pendingUsers();
            return ResponseEntity.status(HttpStatus.OK).body(pendingUsers);
        } catch (Exception e){
            throw new RuntimeException("Unable to fetch pending users :"+e.getMessage());
        }
    }

    @PutMapping("/approve/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> approveUser(@PathVariable Long id){
        try {
            approvalService.approveUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("User approved successfully");
        } catch (Exception e){
            throw new RuntimeException("Unable to approve user");
        }
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<String> rejectUser(@PathVariable Long id){
        try {
            approvalService.rejectUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("User rejected successfully");
        } catch (Exception e){
            throw new RuntimeException("Unable to reject user");
        }
    }
}

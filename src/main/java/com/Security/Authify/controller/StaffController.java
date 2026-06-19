package com.Security.Authify.controller;

import com.Security.Authify.entity.Staff;
import com.Security.Authify.io.StudentResponse;
import com.Security.Authify.io.cursorPageResponse;
import com.Security.Authify.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/getAllStaffs")
//    @PreAuthorize("hasAuthority('TEACHER_READ')")
    public ResponseEntity<cursorPageResponse<Staff>> getAllStaffs(
            @RequestParam( required = false ) Long cursor,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        try{
            cursorPageResponse<Staff> response = staffService.getAllStaffs(cursor, size);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new RuntimeException("Failed to get all students cursor :"+e.getMessage());
        }
    }
}

package com.Security.Authify.controller;

import com.Security.Authify.io.TeacherRequest;
import com.Security.Authify.io.TeacherResponse;
import com.Security.Authify.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/createTeacher")
    @PreAuthorize("hasAuthority('TEACHER_CREATE')")
    public ResponseEntity<TeacherResponse> createTeacher(@Valid @RequestBody TeacherRequest request) {
        try{
            TeacherResponse teacher = teacherService.createTeacher(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(teacher);
        } catch (Exception e) {
            throw new RuntimeException("Fail to Create Teacher"+ e.getMessage());
        }
    }

    @GetMapping("/getAllTeachers")
    @PreAuthorize("hasAuthority('TEACHER_READ')")
    public ResponseEntity<List<TeacherResponse>> getAllTeacher(){
        try{
            List<TeacherResponse> res = teacherService.getAllTeachers();
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch(Exception e){
            throw new RuntimeException("Fail to Get All Teachers"+ e.getMessage());
        }
    }

    @GetMapping("/getTeacherById/{id}")
    @PreAuthorize("hasAuthority('TEACHER_READ')")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable Long id){
        try{
            TeacherResponse res = teacherService.getTeacherById(id);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            throw new RuntimeException("Fail to Get Teacher By Id"+ e.getMessage());
        }
    }

    @PutMapping("/updateTeacher/{id}")
    @PreAuthorize("hasAuthority('TEACHER_UPDATE')")
    public ResponseEntity<TeacherResponse> updateTeacher(@Valid @PathVariable Long id, @RequestBody TeacherRequest request){
        try{
            TeacherResponse res = teacherService.updateTeacher(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }catch (AccessDeniedException e) {
            throw e;
        }catch(Exception e) {
            throw new RuntimeException("Fail to Update Teacher :" + e.getMessage());
        }
    }

    @DeleteMapping("/deleteTeacher/{id}")
    @PreAuthorize("hasAuthority('TEACHER_DELETE')")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id){
        try{
            teacherService.deleteTeacher(id);
            return ResponseEntity.status(HttpStatus.OK).body("Teacher deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Fail to Delete Teacher"+ e.getMessage());
        }
    }

}

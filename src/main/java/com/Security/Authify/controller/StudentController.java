package com.Security.Authify.controller;

import com.Security.Authify.io.PaginatedResponse;
import com.Security.Authify.io.StudentRequest;
import com.Security.Authify.io.StudentResponse;
import com.Security.Authify.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/createStudent")
    @PreAuthorize("hasAuthority('STUDENT_CREATE')")
    public ResponseEntity<StudentResponse> saveStudent(@Valid @RequestBody StudentRequest request){
        try{
            StudentResponse response = studentService.createStudent(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            throw new RuntimeException("Failed to create student :" +e.getMessage());
        }
    }

    @GetMapping("/getAllStudents")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<PaginatedResponse<StudentResponse>> getAllStudent(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search
    ){
        try{
            Sort sort = null;
            if(sortDir.equalsIgnoreCase("asc")){
                sort = Sort.by(sortBy).ascending();
            }else{
                sort = Sort.by(sortBy).descending();
            }
            PaginatedResponse<StudentResponse> response = studentService.getAllStudent(PageRequest.of(pageNo-1, pageSize, sort), search);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new RuntimeException("Failed to get all students :" +e.getMessage());
        }
    }

    @GetMapping("/getStudentByStdId/{stdId}")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable String stdId){
        try{
            StudentResponse response = studentService.getStudentById(stdId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new RuntimeException("Failed to get student by id :" +e.getMessage());
        }
    }

    @PutMapping("/updateStudent/{stdId}")
    @PreAuthorize("hasAuthority('STUDENT_UPDATE')")
    public ResponseEntity<StudentResponse> updateStudent(@Valid @RequestBody StudentRequest student, @PathVariable String stdId){
        try{
            StudentResponse response = studentService.updateStudent(student, stdId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (AccessDeniedException e){
            throw e;
        }
        catch (Exception e){
            throw new RuntimeException("Failed to update student :" +e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudent/{stdId}")
    @PreAuthorize("hasAuthority('STUDENT_DELETE')")
    public ResponseEntity<String> deleteStudent(@PathVariable String stdId){
        try{
            studentService.deleteStudent(stdId);
            return ResponseEntity.status(HttpStatus.OK).body("Student deleted successfully");
        }catch (AccessDeniedException e){
            throw e;
        }
        catch (Exception e){
            throw new RuntimeException("Failed to delete student :" +e.getMessage());
        }
    }
}

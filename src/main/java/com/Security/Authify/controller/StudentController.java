package com.Security.Authify.controller;

import com.Security.Authify.io.StudentRequest;
import com.Security.Authify.io.StudentResponse;
import com.Security.Authify.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<StudentResponse>> getAllStudent(){
        try{
            List<StudentResponse> response = studentService.getAllStudent();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new RuntimeException("Failed to get all students :" +e.getMessage());
        }
    }

    @GetMapping("/getStudentById/{id}")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id){
        try{
            StudentResponse response = studentService.getStudentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new RuntimeException("Failed to get student by id :" +e.getMessage());
        }
    }

    @PutMapping("/updateStudent/{id}")
    @PreAuthorize("hasAuthority('STUDENT_UPDATE')")
    public ResponseEntity<StudentResponse> updateStudent(@Valid @RequestBody StudentRequest student, @PathVariable Long id){
        try{
            StudentResponse response = studentService.updateStudent(student, id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            throw new RuntimeException("Failed to update student :" +e.getMessage());
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    @PreAuthorize("hasAuthority('STUDENT_DELETE')")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        try{
            studentService.deleteStudent(id);
            return ResponseEntity.status(HttpStatus.OK).body("Student deleted successfully");
        }catch (Exception e){
            throw new RuntimeException("Failed to delete student :" +e.getMessage());
        }
    }
}

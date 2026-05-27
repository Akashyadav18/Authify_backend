package com.Security.Authify.service;

import com.Security.Authify.io.StudentRequest;
import com.Security.Authify.io.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest student);

    List<StudentResponse> getAllStudent();

    StudentResponse getStudentById(Long id);

    StudentResponse updateStudent(StudentRequest student, Long id);

    void deleteStudent(Long id);
}

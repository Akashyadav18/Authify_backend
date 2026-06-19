package com.Security.Authify.service;

import com.Security.Authify.io.PaginatedResponse;
import com.Security.Authify.io.StudentRequest;
import com.Security.Authify.io.StudentResponse;
import com.Security.Authify.io.cursorPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest student);

    PaginatedResponse<StudentResponse> getAllStudent(Pageable pageable, String search);

    cursorPageResponse<StudentResponse> getAllStudentsCursor(Long cursor, int size);

    StudentResponse getStudentById(String stdId);

    StudentResponse updateStudent(StudentRequest student, String stdId);

    void deleteStudent(String stdId);
}

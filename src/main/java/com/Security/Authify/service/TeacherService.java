package com.Security.Authify.service;

import com.Security.Authify.io.TeacherRequest;
import com.Security.Authify.io.TeacherResponse;

import java.util.List;

public interface TeacherService {

    TeacherResponse createTeacher(TeacherRequest request);

    List<TeacherResponse> getAllTeachers();

    TeacherResponse getTeacherById(Long id);

    TeacherResponse updateTeacher(Long id, TeacherRequest request, String email);

    void deleteTeacher(Long id);
}

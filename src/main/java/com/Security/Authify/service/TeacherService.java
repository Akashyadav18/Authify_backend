package com.Security.Authify.service;

import com.Security.Authify.io.PaginatedResponse;
import com.Security.Authify.io.TeacherRequest;
import com.Security.Authify.io.TeacherResponse;

import java.util.Date;
import java.util.List;

public interface TeacherService {

    TeacherResponse createTeacher(TeacherRequest request);

    PaginatedResponse<TeacherResponse> getAllTeachers(int pageNo, int pageSize, String sortBy, String sortDir,
                                                      Long id, String name, String qualification, Date startDate,Date endDate);

    TeacherResponse getTeacherById(Long id);

    TeacherResponse updateTeacher(Long id, TeacherRequest request);

    void deleteTeacher(Long id);
}

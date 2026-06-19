package com.Security.Authify.service;

import com.Security.Authify.io.PaginatedResponse;
import com.Security.Authify.io.TeacherRequest;
import com.Security.Authify.io.TeacherResponse;
import com.Security.Authify.io.cursorPageResponse;

import java.util.Date;

public interface TeacherService {

    TeacherResponse createTeacher(TeacherRequest request);

    PaginatedResponse<TeacherResponse> getAllTeachers(int pageNo, int pageSize, String sortBy, String sortDir,
                                                      Long id, String name, String qualification, Date startDate,Date endDate);

    cursorPageResponse<TeacherResponse> getAllTeachersCursor(Long cursor, int size);

    TeacherResponse getTeacherById(Long id);

    TeacherResponse updateTeacher(Long id, TeacherRequest request);

    void deleteTeacher(Long id);
}

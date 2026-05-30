package com.Security.Authify.service;

import com.Security.Authify.entity.TeacherEntity;
import com.Security.Authify.io.TeacherMapper;
import com.Security.Authify.io.TeacherRequest;
import com.Security.Authify.io.TeacherResponse;
import com.Security.Authify.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponse createTeacher(TeacherRequest request) {
        TeacherEntity teacher = teacherMapper.convertToTeacherEntity(request);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.convertToTeacherResponse(teacher);
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacherMapper::convertToTeacherResponse)
                .toList();
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return teacherMapper.convertToTeacherResponse(teacher);
    }

    @Override
    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher Not Found! "+id));
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setQualification(request.getQualification());
        teacher.setGender(request.getGender());
        teacher.setExperienceYear(request.getExperienceYear());
        teacher = teacherRepository.save(teacher);

        return teacherMapper.convertToTeacherResponse(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        TeacherEntity teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        teacherRepository.delete(teacher);
    }


}

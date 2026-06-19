package com.Security.Authify.service;

import com.Security.Authify.entity.TeacherEntity;
import com.Security.Authify.io.*;
import com.Security.Authify.repository.TeacherRepository;
import com.Security.Authify.specification.TeacherSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public PaginatedResponse<TeacherResponse> getAllTeachers(int pageNo, int pageSize, String sortBy, String sortDir, Long id, String name, String qualification, Date startDate, Date endDate) {
        Sort sort = sortDir.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Specification<TeacherEntity> spec = TeacherSpecification.teachSpecification(id, name, qualification, startDate, endDate);
        Page<TeacherEntity> teachPage = teacherRepository.findAll(spec, pageable);
        List<TeacherResponse> teacherList = teachPage.getContent()
                .stream()
                .map(teacherMapper::convertToTeacherResponse)
                .toList();
        return PaginatedResponse.of(teacherList, teachPage);
    }

    public cursorPageResponse<TeacherResponse> getAllTeachersCursor(Long cursor, int size) {
//        default page =0, size=10 [0-9]
        Pageable pageable = PageRequest.of(0, size);
//        fetch next page records
        List<TeacherEntity> teacherEntity = teacherRepository.fetchNextPage(cursor, pageable);
        List<TeacherResponse> teacherList = teacherEntity.stream()
                .map(teacherMapper::convertToTeacherResponse)
                .collect(Collectors.toList());
//        check if have more records
        boolean hasNext = teacherList.size() == size;
        Long nextCursor = hasNext ? teacherList.get(size - 1).getId() : null;
        
        return new cursorPageResponse<>(teacherList, size, nextCursor, hasNext);
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

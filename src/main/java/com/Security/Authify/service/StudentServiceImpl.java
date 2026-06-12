package com.Security.Authify.service;

import com.Security.Authify.entity.StudentEntity;
import com.Security.Authify.io.PaginatedResponse;
import com.Security.Authify.io.StudentMapper;
import com.Security.Authify.io.StudentRequest;
import com.Security.Authify.io.StudentResponse;
import com.Security.Authify.jwtUtils.AuthUtil;
import com.Security.Authify.repository.StudentRepository;
import com.Security.Authify.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService{

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepo;
    private final AuthUtil authUtil;

    @Override
    public StudentResponse createStudent(StudentRequest student) {
        StudentEntity stu = studentMapper.convertToStuEntity(student);
        stu = studentRepo.save(stu);
        log.info("Student saved in DB");
        return studentMapper.convertToStdResponse(stu);
    }

    @Override
    public PaginatedResponse<StudentResponse> getAllStudent(Pageable pageable, String search) {
        Specification<StudentEntity> spec = StudentSpecification.getSpecification(search);
            Page<StudentEntity> stuPage = studentRepo.findAll(spec, pageable);
            List<StudentResponse> studentList = stuPage.getContent()
                    .stream()
                    .map(studentMapper::convertToStdResponse)
                    .toList();
            return PaginatedResponse.of(studentList, stuPage);
    }

    @Override
    public StudentResponse getStudentById(String stdId) {
        StudentEntity stuById = studentRepo.findByStdId(stdId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return studentMapper.convertToStdResponse(stuById);
    }

    @Override
    public StudentResponse updateStudent(StudentRequest student, String stdId) {
        StudentEntity stud = studentRepo.findByStdId(stdId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String currentUserId = authUtil.getCurrentUserId();
        boolean isCreator = currentUserId.equals(stud.getCreatedBy());
        boolean isAdmin = authUtil.isAdmin();
        if(!isCreator && !isAdmin){
            throw new AccessDeniedException("You do not have permission to perform this action");
        }

        stud.setFirstName(student.getFirstName());
        stud.setLastName(student.getLastName());
        stud.setRollNo(student.getRollNo());
        stud.setGender(student.getGender());
        stud = studentRepo.save(stud);
        return studentMapper.convertToStdResponse(stud);
    }

    @Override
    public void deleteStudent(String stdId) {
        StudentEntity student = studentRepo.findByStdId(stdId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String currentUserId = authUtil.getCurrentUserId();
        boolean isCreator = currentUserId.equals(student.getCreatedBy());
        boolean isAdmin = authUtil.isAdmin();
        if(!isCreator && !isAdmin){
            throw new AccessDeniedException("You do not have permission to perform this action");
        }

        studentRepo.delete(student);
        log.info("Student deleted from DB");
    }


}

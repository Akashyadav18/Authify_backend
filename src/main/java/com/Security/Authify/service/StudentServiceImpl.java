package com.Security.Authify.service;

import com.Security.Authify.entity.StudentEntity;
import com.Security.Authify.io.StudentMapper;
import com.Security.Authify.io.StudentRequest;
import com.Security.Authify.io.StudentResponse;
import com.Security.Authify.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService{

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepo;

    @Override
    public StudentResponse createStudent(StudentRequest student) {
        StudentEntity stu = studentMapper.convertToStuEntity(student);
        stu = studentRepo.save(stu);
        log.info("Student saved in DB");
        return studentMapper.convertToStdResponse(stu);
    }

    @Override
    public List<StudentResponse> getAllStudent() {
        return studentRepo.findAll()
                .stream()
                .map(studentMapper::convertToStdResponse)
                .toList();
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        StudentEntity stuById = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return studentMapper.convertToStdResponse(stuById);
    }

    @Override
    public StudentResponse updateStudent(StudentRequest student, Long id) {
        StudentEntity stud = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        stud.setFirstName(student.getFirstName());
        stud.setLastName(student.getLastName());
        stud.setRollNo(student.getRollNo());
        stud.setGender(student.getGender());
        stud = studentRepo.save(stud);
        return studentMapper.convertToStdResponse(stud);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentRepo.deleteById(id);
        log.info("Student deleted from DB");
    }


}

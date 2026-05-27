package com.Security.Authify.io;

import com.Security.Authify.entity.StudentEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StudentMapper {

    public StudentEntity convertToStuEntity(StudentRequest request){
        return StudentEntity.builder()
                .stdId(UUID.randomUUID().toString())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .rollNo(request.getRollNo())
                .gender(request.getGender())
                .build();
    }

    public StudentResponse convertToStdResponse(StudentEntity response){
        return StudentResponse.builder()
                .fullName(response.getFirstName() + " " + response.getLastName())
                .rollNo(response.getRollNo())
                .gender(response.getGender())
                .stdId(response.getStdId())
                .id(response.getId())
                .build();
    }
}

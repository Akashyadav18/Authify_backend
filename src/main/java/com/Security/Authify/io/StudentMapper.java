package com.Security.Authify.io;

import com.Security.Authify.custom.CustomGenerator;
import com.Security.Authify.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMapper {

    private final CustomGenerator customGenerator;

    public StudentEntity convertToStuEntity(StudentRequest request){
        return StudentEntity.builder()
                .stdId(customGenerator.generateUniqueId())
                .username(customGenerator.generateUniqueName(request.getFirstName(), request.getRollNo()))
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
                .username(response.getUsername())
                .id(response.getId())
                .build();
    }
}

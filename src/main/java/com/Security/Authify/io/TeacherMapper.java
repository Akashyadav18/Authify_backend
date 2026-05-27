package com.Security.Authify.io;

import com.Security.Authify.entity.TeacherEntity;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class TeacherMapper {

    public TeacherEntity convertToTeacherEntity(TeacherRequest request){
        return TeacherEntity.builder()
                .teacherId(UUID.randomUUID().toString())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .experienceYear(request.getExperienceYear())
                .qualification(request.getQualification())
                .gender(request.getGender())
                .build();
    }

    public TeacherResponse convertToTeacherResponse(TeacherEntity entity){
        return TeacherResponse.builder()
                .id(entity.getId())
                .teacherId(entity.getTeacherId())
                .fullName(entity.getFirstName()+" "+entity.getLastName())
                .experienceYear(entity.getExperienceYear())
                .qualification(entity.getQualification())
                .gender(entity.getGender())
                .build();
    }
}

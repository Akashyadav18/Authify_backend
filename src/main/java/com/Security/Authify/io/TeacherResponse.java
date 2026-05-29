package com.Security.Authify.io;

import com.Security.Authify.entity.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherResponse {
    private Long id;
    private String teacherId;
    private String fullName;
    private int experienceYear;
    private String qualification;
    private String phoneNumber;
    private GenderEnum gender;
    private String createdBy;
}

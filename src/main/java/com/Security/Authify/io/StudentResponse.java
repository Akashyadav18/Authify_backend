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
public class StudentResponse {
    private Long id;
    private String stdId;
    private String fullName;
    private String rollNo;
    private GenderEnum gender;
}

package com.Security.Authify.io;

import com.Security.Authify.entity.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotBlank(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotNull(message = "Roll number is required")
    @Pattern(regexp = "^\\d+$", message = "Roll number must be a number")
    private String rollNo;
    @NotNull(message = "Gender is required, can only be Male, Female, Other")
    private GenderEnum gender;
}

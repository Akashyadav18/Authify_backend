package com.Security.Authify.io;

import com.Security.Authify.entity.GenderEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherRequest {

    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;
    @NotNull(message = "Experience is required")
    @Min(value = 1, message = "Experience year must be greater than 0")
    private int experienceYear;

    @NotBlank(message = "Qualification is required")
    private String qualification;
    @NotNull(message = "Gender is required, male, female and other")
    private GenderEnum gender;

}

package com.Security.Authify.io;

import com.Security.Authify.entity.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    @NotBlank(message = "Name should not be empty")
    private String name;
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
            message = "Invalid email format")
    @NotNull(message = "Email should not be empty")
    private String email;
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;
    @NotNull(message = "Role should not be empty")
    private Role role;

}

package com.Security.Authify.io;

import com.Security.Authify.entity.ERole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class InvitationRequest {

    @Email(message = "Invalid Email format")
    @NotNull(message = "Email cannot be Empty")
    private String email;
    @NotNull(message = "Role should not be empty")
    private ERole role;
}

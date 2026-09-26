package com.Security.Authify.io;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Valid
public class SetupAccountRequest {
    @NotBlank(message = "Token cannot be empty")
    private String token;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Name cannot be empty")
    private String name;
}

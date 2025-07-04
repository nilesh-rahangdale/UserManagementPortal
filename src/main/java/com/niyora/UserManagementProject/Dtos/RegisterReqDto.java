package com.niyora.UserManagementProject.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterReqDto {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min=8, message = "Password must be at least 8 characters long",max =16)
    private String password;

    @NotBlank
    @Email
    private String email;
}

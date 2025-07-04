package com.niyora.UserManagementProject.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReqDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}

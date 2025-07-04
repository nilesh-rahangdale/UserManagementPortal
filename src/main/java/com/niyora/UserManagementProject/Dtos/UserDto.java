package com.niyora.UserManagementProject.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private Long id;

    @NotBlank
    @Size(min=3, max=50)
    private String username;

    @NotBlank
    @Size(max=70)
    @Email
    private String email;
}

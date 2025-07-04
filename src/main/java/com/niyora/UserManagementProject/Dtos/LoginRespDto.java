package com.niyora.UserManagementProject.Dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRespDto {
    private String jwtToken;
    private UserDto userDto;
}

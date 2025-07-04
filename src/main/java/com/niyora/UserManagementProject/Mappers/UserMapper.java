package com.niyora.UserManagementProject.Mappers;

import com.niyora.UserManagementProject.Dtos.UserDto;
import com.niyora.UserManagementProject.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

//@Component
@Mapper(componentModel = "spring")
public interface UserMapper {


    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
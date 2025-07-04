package com.niyora.UserManagementProject.Controllers;

import com.niyora.UserManagementProject.Dtos.RegisterReqDto;
import com.niyora.UserManagementProject.Dtos.UserDto;
import com.niyora.UserManagementProject.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registerAdminUser")
    public ResponseEntity<UserDto> registerAdminUser(@RequestBody RegisterReqDto registerReqDto){
        return ResponseEntity.ok(authenticationService.registerAdminUser(registerReqDto));
    }

}

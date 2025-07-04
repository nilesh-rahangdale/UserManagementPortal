package com.niyora.UserManagementProject.Controllers;

import com.niyora.UserManagementProject.Dtos.LoginReqDto;
import com.niyora.UserManagementProject.Dtos.LoginRespDto;
import com.niyora.UserManagementProject.Dtos.RegisterReqDto;
import com.niyora.UserManagementProject.Dtos.UserDto;
import com.niyora.UserManagementProject.Entity.User;
import com.niyora.UserManagementProject.Repositories.UserRepo;
import com.niyora.UserManagementProject.Services.AuthenticationService;
import com.niyora.UserManagementProject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.HttpCookie;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/registerNormalUser")
    public ResponseEntity<UserDto> registerNormalUser(@RequestBody RegisterReqDto registerReqDto){
        return ResponseEntity.ok(authService.registerNormalUser(registerReqDto));
    }

    @PostMapping("/loginUser")
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginReqDto loginReqDto){
        LoginRespDto loginRespDto= authService.loginUser(loginReqDto);

        ResponseCookie responseCookie=ResponseCookie.from("JwtToken", loginRespDto.getJwtToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60*60*1) // 1 hour
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginRespDto.getUserDto());
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<String> logoutUser(){
        return authService.logOutUser();
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<?> getCurrentUser(Authentication authentication){
        if(authentication ==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not Authorized");
        }

        String username=authentication.getName();
        UserDto user=userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }


}

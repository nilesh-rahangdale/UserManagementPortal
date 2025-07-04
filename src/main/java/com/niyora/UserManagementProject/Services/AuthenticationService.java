package com.niyora.UserManagementProject.Services;

import com.niyora.UserManagementProject.Dtos.LoginReqDto;
import com.niyora.UserManagementProject.Dtos.LoginRespDto;
import com.niyora.UserManagementProject.Dtos.RegisterReqDto;
import com.niyora.UserManagementProject.Dtos.UserDto;
import com.niyora.UserManagementProject.Entity.User;
import com.niyora.UserManagementProject.Mappers.UserMapper;
import com.niyora.UserManagementProject.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto registerNormalUser(RegisterReqDto registerReqDto) {
        if(userRepo.existsByUsername(registerReqDto.getUsername())){
            throw new RuntimeException("User has been already registered with username"+registerReqDto.getUsername());
        }
        User user= new User();
        user.setEmail(registerReqDto.getEmail());
        user.setUsername(registerReqDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerReqDto.getPassword()));
        user.setRoles(new HashSet<>(Collections.singleton("ROLE_USER")));
        User savedUser=userRepo.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public UserDto registerAdminUser(RegisterReqDto registerReqDto) {
        if(userRepo.existsByUsername(registerReqDto.getUsername())){
            throw new RuntimeException("User has been already registered with username"+registerReqDto.getUsername());
        }
        User user= new User();
        user.setEmail(registerReqDto.getEmail());
        user.setUsername(registerReqDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerReqDto.getPassword()));
        Set<String> roles= new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        user.setRoles(roles);
        User savedUser=userRepo.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public LoginRespDto loginUser(LoginReqDto loginReqDto) {
    User user = userRepo.findByUsername(loginReqDto.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found with username: " + loginReqDto.getUsername()));
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(),loginReqDto.getPassword()));
    String jwt=jwtService.generateToken(user);
        return LoginRespDto.builder()
                .jwtToken(jwt)
                .userDto(userMapper.toUserDto(user))
                .build();
    }

    public ResponseEntity<String> logOutUser() {
        ResponseCookie cookie=ResponseCookie.from("JwtToken","")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0) // Set max age to 0 to delete the cookie
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body("User logged out successfully");
    }


}

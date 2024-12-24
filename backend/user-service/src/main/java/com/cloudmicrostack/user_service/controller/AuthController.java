package com.cloudmicrostack.user_service.controller;

import com.cloudmicrostack.user_service.dto.CreateUserRequest;
import com.cloudmicrostack.user_service.dto.UserDto;
import com.cloudmicrostack.user_service.dto.auth.LoginRequest;
import com.cloudmicrostack.user_service.dto.auth.LoginResponse;
import com.cloudmicrostack.user_service.dto.auth.UserInfo;
import com.cloudmicrostack.user_service.service.AuthService;
import com.cloudmicrostack.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
}
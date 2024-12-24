package com.cloudmicrostack.user_service.service;

import com.cloudmicrostack.user_service.dto.auth.LoginRequest;
import com.cloudmicrostack.user_service.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
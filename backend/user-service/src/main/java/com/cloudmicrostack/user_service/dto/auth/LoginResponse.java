package com.cloudmicrostack.user_service.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private UserInfo user;
}
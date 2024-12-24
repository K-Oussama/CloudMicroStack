package com.cloudmicrostack.user_service.dto.auth;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class UserInfo {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
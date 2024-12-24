package com.cloudmicrostack.user_service.dto;

import lombok.Data;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private Set<String> roles;
    private boolean enabled;
}
package com.cloudmicrostack.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Size(min = 3, max = 50)
    private String firstName;

    @Size(min = 3, max = 50)
    private String lastName;

    @Email
    private String email;

    @Size(min = 6, max = 40)
    private String password;

    private String phoneNumber;
    private String address;
}
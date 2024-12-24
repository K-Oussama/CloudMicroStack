package com.cloudmicrostack.user_service.service;

import com.cloudmicrostack.user_service.dto.CreateUserRequest;
import com.cloudmicrostack.user_service.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto createUser(CreateUserRequest request);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    Page<UserDto> getAllUsers(Pageable pageable);
    UserDto updateUser(Long id, CreateUserRequest request);
    void deleteUser(Long id);
    void enableUser(Long id);
    void disableUser(Long id);
    void addRole(Long id, String role);
    void removeRole(Long id, String role);
}
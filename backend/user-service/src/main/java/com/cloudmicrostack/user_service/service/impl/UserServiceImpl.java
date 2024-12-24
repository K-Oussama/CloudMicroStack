// src/main/java/com/cloudmicrostack/user_service/service/impl/UserServiceImpl.java
package com.cloudmicrostack.user_service.service.impl;

import com.cloudmicrostack.user_service.dto.CreateUserRequest;
import com.cloudmicrostack.user_service.dto.UserDto;
import com.cloudmicrostack.user_service.entity.Role;
import com.cloudmicrostack.user_service.entity.User;
import com.cloudmicrostack.user_service.exception.ResourceNotFoundException;
import com.cloudmicrostack.user_service.exception.UserAlreadyExistsException;
import com.cloudmicrostack.user_service.repository.UserRepository;
import com.cloudmicrostack.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .roles(new HashSet<>(Collections.singletonList(Role.ROLE_USER.name())))
                .enabled(true)
                .build();

        return convertToDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(Long id) {
        return convertToDto(findUserById(id));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return convertToDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email)));
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToDto);
    }

    @Override
    public UserDto updateUser(Long id, CreateUserRequest request) {
        User user = findUserById(id);
        
        if (!user.getEmail().equals(request.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return convertToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(findUserById(id));
    }

    @Override
    public void enableUser(Long id) {
        User user = findUserById(id);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(Long id) {
        User user = findUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void addRole(Long id, String role) {
        User user = findUserById(id);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRole(Long id, String role) {
        User user = findUserById(id);
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setRoles(user.getRoles());
        dto.setEnabled(user.isEnabled());
        return dto;
    }
}
package com.cloudmicrostack.user_service.service.impl;

import com.cloudmicrostack.user_service.dto.auth.LoginRequest;
import com.cloudmicrostack.user_service.dto.auth.LoginResponse;
import com.cloudmicrostack.user_service.dto.auth.UserInfo;
import com.cloudmicrostack.user_service.entity.User;
import com.cloudmicrostack.user_service.repository.UserRepository;
import com.cloudmicrostack.user_service.security.JwtService;
import com.cloudmicrostack.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String jwt = jwtService.generateToken(
                org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(user.getRoles().stream()
                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role))
                        .toList())
                    .build()
            );

            return LoginResponse.builder()
                .token(jwt)
                .user(UserInfo.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .roles(user.getRoles())
                    .build())
                .build();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email/password", e);
        }
    }
}
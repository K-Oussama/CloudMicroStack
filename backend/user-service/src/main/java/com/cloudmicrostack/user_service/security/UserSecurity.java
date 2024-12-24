package com.cloudmicrostack.user_service.security;

import com.cloudmicrostack.user_service.dto.UserDto;
import com.cloudmicrostack.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {
    private final UserService userService;

    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDto user = userService.getUserByEmail(userDetails.getUsername());
        return user.getId().equals(userId);
    }
}
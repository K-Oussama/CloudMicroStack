// src/main/java/com/cloudmicrostack/user_service/controller/WelcomeController.java
package com.cloudmicrostack.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public Map<String, String> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to User Service");
        response.put("status", "UP");
        response.put("timestamp", java.time.Instant.now().toString());
        return response;
    }

    @GetMapping("/api/test")
    public Map<String, String> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "User Service is working!");
        response.put("status", "UP");
        response.put("timestamp", java.time.Instant.now().toString());
        return response;
    }
}
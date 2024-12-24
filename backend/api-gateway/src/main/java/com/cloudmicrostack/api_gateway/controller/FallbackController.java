package com.cloudmicrostack.api_gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping(value = "/api/fallback", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> fallback() {
        return Mono.just(Map.of(
            "status", "UP",
            "message", "API Gateway is operational",
            "timestamp", java.time.Instant.now().toString(),
            "service", "API Gateway",
            "endpoint", "/api/fallback"
        ));
    }

    @GetMapping(value = "/api/welcome", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> welcome() {
        return Mono.just(Map.of(
            "status", "UP",
            "message", "Welcome to API Gateway",
            "timestamp", java.time.Instant.now().toString(),
            "service", "API Gateway",
            "version", "1.0"
        ));
    }
}
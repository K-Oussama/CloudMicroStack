package com.cloudmicrostack.api_gateway.config;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ErrorHandlerConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    @Order(-1)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ServerCodecConfigurer serverCodecConfigurer) {
        return (ServerWebExchange exchange, Throwable ex) -> {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("path", exchange.getRequest().getPath().value());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred");

            byte[] bytes;
            try {
                bytes = objectMapper.writeValueAsBytes(errorResponse);
            } catch (Exception e) {
                bytes = "Internal Server Error".getBytes();
            }

            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

            return exchange.getResponse().writeWith(Mono.just(buffer));
        };
    }
}
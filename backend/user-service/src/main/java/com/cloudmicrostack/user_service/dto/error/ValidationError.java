// src/main/java/com/cloudmicrostack/user_service/dto/error/ValidationError.java
package com.cloudmicrostack.user_service.dto.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {
    private String field;
    private String message;
}
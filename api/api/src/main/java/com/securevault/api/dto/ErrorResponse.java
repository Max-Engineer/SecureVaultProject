package com.securevault.api.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        int status,
        String error,
        String message,
        Map<String, String> details, // Used for field-specific validation errors
        LocalDateTime timestamp
) {}
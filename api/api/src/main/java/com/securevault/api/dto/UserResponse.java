package com.securevault.api.dto;

import java.time.LocalDateTime;

/**
 * A safe representation of a User.
 */
public record UserResponse(
        Long id,
        String email,
        String fullName,
        LocalDateTime createdAt
) {}
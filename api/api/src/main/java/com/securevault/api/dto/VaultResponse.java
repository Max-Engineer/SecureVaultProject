package com.securevault.api.dto;

import java.time.LocalDateTime;

public record VaultResponse(
        Long id,
        String name,
        String description,
        Long organizationId,
        LocalDateTime createdAt
) {}
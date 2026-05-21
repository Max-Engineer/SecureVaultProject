package com.securevault.api.dto;

import jakarta.validation.constraints.NotBlank;

public record SecretUpdateRequest(
        @NotBlank(message = "New secret value is required")
        String secretValue
) {}
package com.securevault.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VaultRequest(
        @NotBlank(message = "Vault name is required")
        String name,

        String description,

        @NotNull(message = "Organization ID is required")
        Long organizationId
) {}
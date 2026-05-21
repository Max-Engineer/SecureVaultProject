package com.securevault.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SecretRequest(
        @NotBlank(message = "Secret key is required")
        String secretKey,

        @NotBlank(message = "Secret value is required")
        String secretValue,

        @NotNull(message = "Vault ID is required")
        Long vaultId
) {}
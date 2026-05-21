package com.securevault.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrganizationResponse(
        Long id,
        String name,
        List<VaultResponse> vaults,
        LocalDateTime createdAt
) {}
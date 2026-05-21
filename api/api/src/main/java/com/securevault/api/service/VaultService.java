package com.securevault.api.service;

import com.securevault.api.dto.VaultRequest;
import com.securevault.api.model.Organization;
import com.securevault.api.model.Vault;
import com.securevault.api.repository.OrganizationRepository;
import com.securevault.api.repository.VaultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VaultService {

    private final VaultRepository vaultRepository;
    private final OrganizationRepository organizationRepository;

    public Vault createVault(VaultRequest request, String authenticatedEmail) {
        // 1. Fetch target organization
        Organization organization = organizationRepository.findById(request.organizationId())
                .orElseThrow(() -> new IllegalArgumentException("Organization not found with ID: " + request.organizationId()));

        // 2. Security Boundary: Verify ownership
        if (!organization.getOwner().getEmail().equals(authenticatedEmail)) {
            throw new AccessDeniedException("You do not have permission to create a vault in this organization.");
        }

        // 3. Map and save entity
        Vault vault = Vault.builder()
                .name(request.name())
                .description(request.description())
                .organization(organization)
                .build();

        return vaultRepository.save(vault);
    }
}
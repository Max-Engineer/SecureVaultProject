package com.securevault.api.service;

import com.securevault.api.dto.OrganizationRequest;
import com.securevault.api.dto.OrganizationResponse;
import com.securevault.api.dto.VaultResponse;
import com.securevault.api.model.Organization;
import com.securevault.api.model.User;
import com.securevault.api.repository.OrganizationRepository;
import com.securevault.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(org -> new OrganizationResponse(
                        org.getId(),
                        org.getName(),
                        org.getVaults().stream()
                                .map(v -> new VaultResponse(
                                        v.getId(),
                                        v.getName(),
                                        v.getDescription(),
                                        org.getId(),
                                        v.getCreatedAt()))
                                .toList(),
                        org.getCreatedAt()
                ))
                .toList();
    }

    public Organization createOrganization(OrganizationRequest request, String ownerEmail) {
        // 1. Fetch the user from the database to establish the relationship
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + ownerEmail));

        // 2. Map the DTO to our Entity
        Organization organization = new Organization();
        organization.setName(request.name());
        organization.setOwner(owner); // Linking the organization to the user

        // 3. Save to PostgreSQL
        return organizationRepository.save(organization);
    }
}
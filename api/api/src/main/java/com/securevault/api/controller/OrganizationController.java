package com.securevault.api.controller;

import com.securevault.api.dto.OrganizationRequest;
import com.securevault.api.dto.OrganizationResponse;
import com.securevault.api.model.Organization;
import com.securevault.api.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping
    public List<OrganizationResponse> getAll() {
        return organizationService.getAllOrganizations();
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(
            @RequestBody OrganizationRequest request,
            Principal principal) {

        // principal.getName() automatically returns the email we put inside the JWT token!
        String email = principal.getName();

        Organization savedOrg = organizationService.createOrganization(request, email);
        return ResponseEntity.ok(savedOrg);
    }
}
package com.securevault.api.controller;

import com.securevault.api.dto.VaultRequest;
import com.securevault.api.model.Vault;
import com.securevault.api.service.VaultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/vaults")
@RequiredArgsConstructor
public class VaultController {

    private final VaultService vaultService;

    @PostMapping
    public ResponseEntity<Vault> createVault(
            @Valid @RequestBody VaultRequest request,
            Principal principal) {

        String email = principal.getName();
        Vault savedVault = vaultService.createVault(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVault);
    }
}
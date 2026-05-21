package com.securevault.api.controller;

import com.securevault.api.dto.SecretRequest;
import com.securevault.api.dto.SecretUpdateRequest;
import com.securevault.api.model.Secret;
import com.securevault.api.service.SecretService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/secrets")
@RequiredArgsConstructor
public class SecretController {

    private final SecretService secretService;

    @PostMapping
    public ResponseEntity<Secret> createSecret(@Valid @RequestBody SecretRequest request, Principal principal) {
        Secret savedSecret = secretService.saveSecret(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSecret);
    }

    @GetMapping("/vault/{vaultId}")
    public ResponseEntity<List<Secret>> getVaultSecrets(@PathVariable Long vaultId, Principal principal) {
        List<Secret> secrets = secretService.getSecretsByVault(vaultId, principal.getName());
        return ResponseEntity.ok(secrets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Secret> updateSecret(
            @PathVariable Long id,
            @Valid @RequestBody SecretUpdateRequest request,
            Principal principal) {

        Secret updated = secretService.updateSecret(id, request.secretValue(), principal.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecret(@PathVariable Long id, Principal principal) {
        secretService.deleteSecret(id, principal.getName());
        return ResponseEntity.noContent().build(); // Returns a clean 244 No Content on success
    }
}
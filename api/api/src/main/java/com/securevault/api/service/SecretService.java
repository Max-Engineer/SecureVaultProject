package com.securevault.api.service;

import com.securevault.api.dto.SecretRequest;
import com.securevault.api.model.Secret;
import com.securevault.api.model.Vault;
import com.securevault.api.repository.SecretRepository;
import com.securevault.api.repository.VaultRepository;
import com.securevault.api.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecretService {

    private final SecretRepository secretRepository;
    private final VaultRepository vaultRepository;
    private final EncryptionUtils encryptionUtils;

    public Secret saveSecret(SecretRequest request, String authenticatedEmail) {
        // 1. Find Vault
        Vault vault = vaultRepository.findById(request.vaultId())
                .orElseThrow(() -> new IllegalArgumentException("Vault not found with ID: " + request.vaultId()));

        // 2. Deep Ownership Verification Check
        String ownerEmail = vault.getOrganization().getOwner().getEmail();
        if (!ownerEmail.equals(authenticatedEmail)) {
            throw new AccessDeniedException("Unauthorized access: You do not own the organization containing this vault.");
        }

        // 3. Encrypt data value before saving
        String encryptedData = encryptionUtils.encrypt(request.secretValue());

        Secret secret = Secret.builder()
                .secretKey(request.secretKey())
                .secretValue(encryptedData)
                .vault(vault)
                .build();

        return secretRepository.save(secret);
    }

    public List<Secret> getSecretsByVault(Long vaultId, String authenticatedEmail) {
        // 1. Fetch the vault
        Vault vault = vaultRepository.findById(vaultId)
                .orElseThrow(() -> new IllegalArgumentException("Vault not found with ID: " + vaultId));

        // 2. Security Check: Does this user own the organization holding this vault?
        String ownerEmail = vault.getOrganization().getOwner().getEmail();
        if (!ownerEmail.equals(authenticatedEmail)) {
            throw new AccessDeniedException("Unauthorized access: You do not have permission to view secrets in this vault.");
        }

        // 3. Retrieve all secrets inside this vault
        List<Secret> secrets = secretRepository.findByVaultId(vaultId);

        // 4. Decrypt values on the fly before sending them back to the user
        secrets.forEach(secret -> {
            String plainText = encryptionUtils.decrypt(secret.getSecretValue());
            secret.setSecretValue(plainText);
        });

        return secrets;
    }

    public Secret updateSecret(Long secretId, String newValue, String authenticatedEmail) {
        // 1. Fetch the target secret
        Secret secret = secretRepository.findById(secretId)
                .orElseThrow(() -> new IllegalArgumentException("Secret not found with ID: " + secretId));

        // 2. Validate Ownership Boundary via the associated Vault
        String ownerEmail = secret.getVault().getOrganization().getOwner().getEmail();
        if (!ownerEmail.equals(authenticatedEmail)) {
            throw new AccessDeniedException("Unauthorized: You do not have permission to modify this secret.");
        }

        // 3. Encrypt the new value and save
        String encryptedValue = encryptionUtils.encrypt(newValue);
        secret.setSecretValue(encryptedValue);

        return secretRepository.save(secret);
    }

    public void deleteSecret(Long secretId, String authenticatedEmail) {
        // 1. Fetch the target secret
        Secret secret = secretRepository.findById(secretId)
                .orElseThrow(() -> new IllegalArgumentException("Secret not found with ID: " + secretId));

        // 2. Validate Ownership Boundary
        String ownerEmail = secret.getVault().getOrganization().getOwner().getEmail();
        if (!ownerEmail.equals(authenticatedEmail)) {
            throw new AccessDeniedException("Unauthorized: You do not have permission to delete this secret.");
        }

        // 3. Complete removal
        secretRepository.delete(secret);
    }
}
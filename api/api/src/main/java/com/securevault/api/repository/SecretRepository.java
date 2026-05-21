package com.securevault.api.repository;

import com.securevault.api.model.Secret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SecretRepository extends JpaRepository<Secret, Long> {
    // Find all secrets inside a specific vault
    List<Secret> findByVaultId(Long vaultId);
}
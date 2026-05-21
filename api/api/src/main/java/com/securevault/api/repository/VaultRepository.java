package com.securevault.api.repository;

import com.securevault.api.model.Vault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {
    // This allows you to find all vaults belonging to one organization
    List<Vault> findByOrganizationId(Long organizationId);
}
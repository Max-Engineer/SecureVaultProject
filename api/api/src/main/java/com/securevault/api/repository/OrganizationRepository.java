package com.securevault.api.repository;

import com.securevault.api.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Helpful for checking if an org exists before trying to create a vault for it
    Optional<Organization> findByName(String name);
}
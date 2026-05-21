package com.securevault.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "secrets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Secret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String secretKey; // e.g., "DATABASE_URL" or "AWS_ACCESS_KEY"

    @Column(nullable = false, length = 1000)
    private String secretValue; // This will hold the encrypted string in the database

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vault_id", nullable = false)
    @JsonIgnore // Prevents infinite JSON serialization loops back to the vault
    private Vault vault;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
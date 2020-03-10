package com.amz.microservices.auth.server.repository;

import com.amz.microservices.auth.server.entity.Credential;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

  boolean existsByUserId(UUID userId);

  boolean existsByUsername(String username);

  Optional<Credential> findByUsername(String username);

  Optional<Credential> findByUserId(UUID userId);

}

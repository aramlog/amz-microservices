package com.amz.microservices.account.server.repository;

import com.amz.microservices.account.server.entity.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {

  Optional<Account> findByIdAndDeleted(UUID uuid, boolean deleted);

  boolean existsByUsername(String username);
}

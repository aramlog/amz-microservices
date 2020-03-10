package com.amz.microservices.auth.server.repository;

import com.amz.microservices.auth.server.entity.RefreshToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByRefreshTokenAndBlocked(String refreshToken, boolean blocked);

  Optional<RefreshToken> findByUserIdAndBlocked(UUID userId, boolean blocked);

}

package com.amz.microservices.auth.server.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "amz_refresh_token")
public class RefreshToken extends AbstractEntity {

  @Column(name = "refresh_token", nullable = false, updatable = false)
  private String refreshToken;

  @Column(name = "blocked")
  private boolean blocked;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "expires_in", nullable = false, updatable = false)
  private LocalDateTime expiresIn;
}

package com.amz.microservices.auth.server.manager;

import com.amz.microservices.common.security.config.properties.JwtConfigProperties;
import com.amz.microservices.common.security.manager.CertificateManager;
import com.amz.microservices.common.security.manager.JwtManager;
import com.amz.microservices.common.security.pojo.AccessTokenClaims;
import com.amz.microservices.common.security.pojo.TokenDetails;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenManager {

  private static final String ROLE_PREFIX = "ROLE_";

  private final JwtConfigProperties jwtConfigProperties;
  private final JwtManager jwtManager;
  private final CertificateManager certificateManager;

  /**
   * Generate access token based on provided user id and authorities
   *
   * @param tokenDetails {@link TokenDetails}
   * @return jwt access token
   */
  public String generateAccessToken(final TokenDetails tokenDetails) {
    final AccessTokenClaims claims = AccessTokenClaims.builder()
        .sub(jwtConfigProperties.getAccessToken().getSubject())
        .iss(jwtConfigProperties.getAccessToken().getIssuer())
        .exp(Instant.now().plus(jwtConfigProperties.getAccessToken().getValidity(), ChronoUnit.MINUTES))
        .authorities(tokenDetails.getRolesNames().stream().map(roleName -> StringUtils.join(ROLE_PREFIX, roleName))
            .collect(Collectors.toSet()))
        .userId(tokenDetails.getUserId())
        .build();

    return jwtManager.generateTokenFromMappedClaims(claims, certificateManager.privateKey(),
        jwtConfigProperties.getSignatureAlgorithm());
  }

}

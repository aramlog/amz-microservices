package com.amz.microservices.common.security.config.properties;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "platform.security.jwt")
public class JwtConfigProperties {

  private String privateKeyPath;
  private String publicKeyPath;
  private String authorizationHeader;
  private String authorizationHeaderPrefix;
  private SignatureAlgorithm signatureAlgorithm;

  private RefreshTokenConfigProperties refreshToken;
  private AccessTokenConfigProperties accessToken;

  /**
   * Claims configuration for authorization tokens.
   */
  @Data
  public static class AccessTokenConfigProperties {

    private String issuer;
    private String subject;
    private Integer validity;
  }

  /**
   * Refresh token validity configuration.
   */
  @Data
  public static class RefreshTokenConfigProperties {

    private Integer validity;
  }

}

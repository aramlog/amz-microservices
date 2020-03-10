package com.amz.microservices.auth.server.service.impl;

import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.auth.dto.RefreshTokenDto;
import com.amz.microservices.auth.dto.RefreshTokenRequestDto;
import com.amz.microservices.auth.server.entity.Credential;
import com.amz.microservices.auth.server.entity.RefreshToken;
import com.amz.microservices.auth.server.error.AuthErrorCode;
import com.amz.microservices.auth.server.exception.AuthException;
import com.amz.microservices.auth.server.manager.TokenManager;
import com.amz.microservices.auth.server.repository.CredentialRepository;
import com.amz.microservices.auth.server.repository.RefreshTokenRepository;
import com.amz.microservices.auth.server.service.AuthService;
import com.amz.microservices.common.security.config.properties.JwtConfigProperties;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final CredentialRepository credentialRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final MapperFacade mapper;
  private final TokenManager tokenManager;
  private final JwtConfigProperties jwtConfigProperties;

  @Override
  @Transactional
  public AuthDto authenticate(final AuthRequestDto request) {
    final Credential credential = credentialRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_BAD_CREDENTIALS));

    // Encrypt the requested password to match with user's one
    final String pwdEncrypted = DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8));

    if (!credential.getPassword().equals(pwdEncrypted)) {
      log.warn("Wrong password for username: {}", request.getPassword());

      throw new AuthException(AuthErrorCode.AUTH_BAD_CREDENTIALS);
    }

    return buildAuthDto(credential);
  }

  @Override
  @Transactional(readOnly = true)
  public RefreshTokenDto refreshToken(final RefreshTokenRequestDto request) {
    // Encrypt the requested password to match with user's one
    final String refreshTokenEncrypted = DigestUtils
        .md5DigestAsHex(request.getRefreshToken().getBytes(StandardCharsets.UTF_8));

    final RefreshToken refreshToken = refreshTokenRepository.findByRefreshTokenAndBlocked(refreshTokenEncrypted, false)
        .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_REFRESH_TOKEN_INVALID));

    if (refreshToken.isBlocked()) {
      log.warn("Refresh token blocked for user with id: {}", refreshToken.getUserId());

      throw new AuthException(AuthErrorCode.AUTH_REFRESH_TOKEN_BLOCKED);
    }

    if (refreshToken.getExpiresIn().isBefore(LocalDateTime.now())) {
      log.warn("Refresh token expired for user with id: {}", refreshToken.getUserId());

      throw new AuthException(AuthErrorCode.AUTH_REFRESH_TOKEN_EXPIRED);
    }

    final Credential credential = credentialRepository.findByUserId(refreshToken.getUserId())
        .orElseThrow(() -> new AuthException(AuthErrorCode.AUTH_CREDENTIALS_NOT_FOUND));

    return RefreshTokenDto.builder()
        .accessToken(tokenManager.generateAccessToken(credential))
        .build();
  }

  @Override
  @Transactional
  public void createCredentials(final CredentialsRequestDto request) {
    if (credentialRepository.existsByUserId(request.getUserId())) {
      log.warn("Account credentials already exists for account with id: {}", request.getUserId());

      throw new AuthException(AuthErrorCode.AUTH_CREDENTIALS_ALREADY_EXISTS);
    }

    if (credentialRepository.existsByUsername(request.getUsername())) {
      log.warn("Account credentials already exists with username: {}", request.getUsername());

      throw new AuthException(AuthErrorCode.AUTH_CREDENTIALS_USERNAME_ALREADY_EXISTS);
    }

    final Credential credential = mapper.map(request, Credential.class);
    credential.setUserType(request.getAccountType().getId());
    credential.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8)));

    credentialRepository.save(credential);

    log.info("Account credentials successfully created for account with id: {}", request.getUserId());
  }

  /**
   * Build {@link AuthDto} based on the given credential
   *
   * @param credential The {@link Credential} details
   * @return {@link AuthDto} with auth details
   */
  private AuthDto buildAuthDto(final Credential credential) {
    // Generate access token based on the given credentials
    final String accessToken = tokenManager.generateAccessToken(credential);

    // Retrieve active refresh token
    final String refreshToken = retrieveRefreshToken(credential.getUserId());

    return AuthDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .tokenType(jwtConfigProperties.getAuthorizationHeaderPrefix())
        .expiresIn(TimeUnit.MINUTES.toSeconds(jwtConfigProperties.getAccessToken().getValidity()))
        .build();
  }

  /**
   * Retrieve refresh token for the given user id
   *
   * Note: Since refreshToken is encrypted, we need to invalidate current active refresh token if it's present and
   * create a new one
   *
   * @param userId The user for which refresh token must be retrieved
   * @return active refresh token
   */
  private String retrieveRefreshToken(final UUID userId) {
    // Invalidate current active refresh token if present
    refreshTokenRepository.findByUserIdAndBlocked(userId, false)
        .ifPresent(activeRefreshToken -> {
          log.info("Deactivating refresh token for user with id: {}", userId);

          activeRefreshToken.setBlocked(true);

          refreshTokenRepository.save(activeRefreshToken);
        });

    final String refreshTokenPlain = UUID.randomUUID().toString();
    final String refreshTokenEncrypted = DigestUtils.md5DigestAsHex(refreshTokenPlain.getBytes(StandardCharsets.UTF_8));

    final RefreshToken activeRefreshToken = new RefreshToken();
    activeRefreshToken.setRefreshToken(refreshTokenEncrypted);
    activeRefreshToken.setUserId(userId);
    activeRefreshToken.setExpiresIn(LocalDateTime.now().plus(jwtConfigProperties.getRefreshToken().getValidity(),
        ChronoUnit.DAYS));

    refreshTokenRepository.save(activeRefreshToken);

    return refreshTokenPlain;
  }

}

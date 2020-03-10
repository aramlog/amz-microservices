package com.amz.microservices.auth.server.controller;

import com.amz.microservices.auth.client.AuthClient;
import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.auth.dto.RefreshTokenDto;
import com.amz.microservices.auth.dto.RefreshTokenRequestDto;
import com.amz.microservices.auth.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthClient {

  private final AuthService authService;

  @Override
  public ResponseEntity<AuthDto> authenticate(final AuthRequestDto request) {
    log.info("Trying to authenticate user with username: {}", request.getUsername());

    final AuthDto response = authService.authenticate(request);

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<RefreshTokenDto> refreshToken(final RefreshTokenRequestDto request) {
    log.info("Trying to refresh token with prefix: {}", request.getRefreshToken().substring(10));

    final RefreshTokenDto response = authService.refreshToken(request);

    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> createCredentials(final CredentialsRequestDto request) {
    log.info("Trying to create credentials for user with id: {} ", request.getUserId());

    authService.createCredentials(request);

    return ResponseEntity.ok().build();
  }

}

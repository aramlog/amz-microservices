package com.amz.microservices.auth.server.service;

import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.auth.dto.RefreshTokenDto;
import com.amz.microservices.auth.dto.RefreshTokenRequestDto;

/**
 * Auth service.
 *
 * @author Aram Kirakosyan
 */
public interface AuthService {

  /**
   * Authenticate credentials
   *
   * @param request {@link AuthRequestDto} which contains the credentials
   * @return {@link AuthDto} if auth succeed
   */
  AuthDto authenticate(AuthRequestDto request);

  /**
   * Refresh the access token
   *
   * @param request {@link RefreshTokenRequestDto} which contains the refresh token
   * @return {@link RefreshTokenDto} which contains refreshed access token
   */
  RefreshTokenDto refreshToken(RefreshTokenRequestDto request);

  /**
   * Create credentials for the given details
   *
   * @param request {@link CredentialsRequestDto} which contains details for credentials
   */
  void createCredentials(CredentialsRequestDto request);

}

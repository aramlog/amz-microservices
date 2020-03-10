package com.amz.microservices.auth.client;

import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.auth.dto.RefreshTokenDto;
import com.amz.microservices.auth.dto.RefreshTokenRequestDto;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;

public class AuthClientMock implements AuthClient {

  @Override
  public ResponseEntity<AuthDto> authenticate(@Valid AuthRequestDto request) {
    return null;
  }

  @Override
  public ResponseEntity<RefreshTokenDto> refreshToken(@Valid RefreshTokenRequestDto refreshToken) {
    return null;
  }

  @Override
  public ResponseEntity<Void> createCredentials(@Valid CredentialsRequestDto request) {
    return null;
  }
}

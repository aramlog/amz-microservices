package com.amz.microservices.auth.client;

import com.amz.microservices.auth.api.AuthApi;
import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.auth.dto.RefreshTokenDto;
import com.amz.microservices.auth.dto.RefreshTokenRequestDto;
import com.amz.microservices.common.contract.feign.FeignClientConfig;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "amz-microservice-auth", configuration = {FeignClientConfig.class})
public interface AuthClient extends AuthApi {

  @Override
  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<AuthDto> authenticate(@RequestBody @Valid AuthRequestDto request);

  @Override
  @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<RefreshTokenDto> refreshToken(@RequestBody @Valid RefreshTokenRequestDto refreshToken);

  @PostMapping(value = "/credentials", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> createCredentials(@RequestBody @Valid CredentialsRequestDto request);

}

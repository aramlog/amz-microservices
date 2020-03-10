package com.amz.microservices.auth.api;

import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.RefreshTokenDto;
import com.amz.microservices.auth.dto.RefreshTokenRequestDto;
import com.amz.microservices.common.object.dto.ErrorResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "Auth")
public interface AuthApi {

  @ApiOperation(value = "Account authentication by credentials")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Successfully logged in", response = AuthDto.class),
      @ApiResponse(code = 400, message = "Missing or erroneous parameter", response = ErrorResponseDto.class),
      @ApiResponse(code = 401, message = "Bad credentials", response = ErrorResponseDto.class),
      @ApiResponse(code = 403, message = "User blocked", response = ErrorResponseDto.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDto.class)
  })
  ResponseEntity<AuthDto> authenticate(
      @ApiParam("Authentication payload") AuthRequestDto request);

  @ApiOperation(value = "Refresh the access token")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Successfully refreshed", response = RefreshTokenDto.class),
      @ApiResponse(code = 400, message = "Missing or erroneous parameter", response = ErrorResponseDto.class),
      @ApiResponse(code = 401, message = "Unknown or invalid refresh token was provided", response = ErrorResponseDto.class),
      @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponseDto.class)
  })
  ResponseEntity<RefreshTokenDto> refreshToken(@ApiParam("Refresh token payload") RefreshTokenRequestDto request);

}

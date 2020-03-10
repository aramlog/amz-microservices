package com.amz.microservices.auth.server.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amz.microservices.auth.dto.AuthDto;
import com.amz.microservices.auth.dto.AuthRequestDto;
import com.amz.microservices.auth.dto.RefreshTokenDto;
import com.amz.microservices.auth.dto.RefreshTokenRequestDto;
import com.amz.microservices.auth.server.config.AbstractAuthIntegrationTest;
import com.amz.microservices.auth.server.error.AuthErrorCode;
import com.amz.microservices.common.object.dto.ErrorResponseDto;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.MediaType;

public class AuthApiAuthIntegrationTests extends AbstractAuthIntegrationTest {

  @Test
  public void shouldLoginSuccess() throws Exception {
    // GIVEN
    final String username = RandomStringUtils.random(10);
    final String password = RandomStringUtils.random(8);

    createCredential(username, password);

    // WHEN
    final AuthDto response = login(username, password);

    // THEN
    assertThat(response).isNotNull();
    assertThat(response.getAccessToken()).isNotNull();
    assertThat(response.getRefreshToken()).isNotNull();
    assertThat(response.getTokenType()).isNotNull();
    assertThat(response.getExpiresIn()).isNotNull();
  }

  @Test
  public void shouldNotLoginBadCredentials() throws Exception {
    // GIVEN
    final String username = RandomStringUtils.random(10);
    final String password = RandomStringUtils.random(8);

    final AuthRequestDto authRequest = new AuthRequestDto(username, password);

    // WHEN
    final ErrorResponseDto response = toObject(mockMvc.perform(post("/login")
            .content(content(authRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andReturn().getResponse().getContentAsString(),
        ErrorResponseDto.class);

    // THEN
    assertThat(response).isNotNull();
    assertThat(response.getErrorSlag()).isNotNull().isEqualTo(AuthErrorCode.AUTH_BAD_CREDENTIALS.name());
  }

  @Test
  public void shouldRefreshTokenSuccess() throws Exception {
    // GIVEN
    String username = RandomStringUtils.random(10);
    String password = RandomStringUtils.random(8);

    createCredential(username, password);

    final AuthDto authResponse = login(username, password);

    final RefreshTokenRequestDto request = new RefreshTokenRequestDto(authResponse.getRefreshToken());

    // WHEN
    final RefreshTokenDto response = toObject(mockMvc.perform(post("/refresh-token")
            .content(content(request))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(),
        RefreshTokenDto.class);

    // THEN
    assertThat(response).isNotNull();
    assertThat(response.getAccessToken()).isNotNull();
  }

  @Test
  public void shouldNotRefreshTokenWhichIsNotExists() throws Exception {
    // GIVEN
    final RefreshTokenRequestDto refreshTokenRequest = new RefreshTokenRequestDto(UUID.randomUUID().toString());

    // WHEN
    final ErrorResponseDto response = toObject(mockMvc.perform(post("/refresh-token")
            .content(content(refreshTokenRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andReturn().getResponse().getContentAsString(),
        ErrorResponseDto.class);

    // THEN
    assertThat(response).isNotNull();
    assertThat(response.getErrorSlag()).isNotNull().isEqualTo(AuthErrorCode.AUTH_REFRESH_TOKEN_INVALID.name());
  }

}

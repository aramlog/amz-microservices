package com.amz.microservices.auth.client;

import com.amz.microservices.auth.api.AuthApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AuthClientConfig {

  @Bean
  @Profile("standalone")
  @ConditionalOnMissingBean(AuthApi.class)
  public AuthApi authContract() {
    return new AuthClientMock();
  }

}

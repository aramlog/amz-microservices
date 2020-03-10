package com.amz.microservices.account.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AccountClientConfig {

  @Bean
  @Profile("standalone")
  @ConditionalOnMissingBean(AccountClient.class)
  public AccountClient authClient() {
    return new AccountClientMock();
  }

}

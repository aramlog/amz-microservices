package com.amz.microservices.product.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProductClientConfig {

  @Bean
  @Profile("standalone")
  @ConditionalOnMissingBean(ProductClient.class)
  public ProductClient productClient() {
    return new ProductClientMock();
  }

}

package com.amz.microservices.auth.server.config;

import com.amz.microservices.auth.dto.CredentialsRequestDto;
import com.amz.microservices.auth.server.entity.Credential;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

  @Bean
  public MapperFacade mapper() {
    final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    mapperFactory.classMap(CredentialsRequestDto.class, Credential.class)
        .exclude("password")
        .exclude("type")
        .byDefault()
        .register();

    return mapperFactory.getMapperFacade();
  }

}

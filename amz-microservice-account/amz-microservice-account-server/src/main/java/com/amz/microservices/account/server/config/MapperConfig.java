package com.amz.microservices.account.server.config;

import com.amz.microservices.account.dto.account.AccountCreateDto;
import com.amz.microservices.account.server.entity.Account;
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

    mapperFactory.classMap(AccountCreateDto.class, Account.class)
        .field("firstName", "profile.firstName")
        .field("lastName", "profile.lastName")
        .field("email", "profile.email")
        .field("mobile", "profile.mobile")
        .field("gender", "profile.gender")
        .field("birthday", "profile.birthday")
        .byDefault()
        .register();


    return mapperFactory.getMapperFacade();
  }

}

package com.amz.microservices.common.config;

import static springfox.documentation.builders.PathSelectors.regex;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Errors;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger", value = "enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String DEFAULT_INCLUDE_PATTERN = "/.*";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .select().apis(RequestHandlerSelectors.basePackage("com.amz.microservices"))
        .paths(Predicates.not(regex("/error")))
        .build()
        .ignoredParameterTypes(Errors.class, ApiIgnore.class)
        .directModelSubstitute(LocalDateTime.class, String.class)
        .directModelSubstitute(Instant.class, String.class)
        .directModelSubstitute(UUID.class, String.class)
        .securityContexts(Lists.newArrayList(securityContext()))
        .securitySchemes(Collections.singletonList(apiKey()))
        .forCodeGeneration(true);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("AMZ.com")
        .description("API Documentation")
        .contact(new Contact("AMZ", "https://amz.com", "itsupport@amz.com"))
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(
        new SecurityReference("JWT", authorizationScopes));
  }
}

package com.amz.microservices.common.security.config;

import com.amz.microservices.common.security.config.properties.JwtConfigProperties;
import com.amz.microservices.common.security.filter.RestAuthorizationFilter;
import com.amz.microservices.common.security.manager.CertificateManager;
import com.amz.microservices.common.security.manager.JwtManager;
import com.amz.microservices.common.security.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off

    http
        .cors()
          .and()
            .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
            .authorizeRequests()
              .antMatchers("/**")
                .permitAll()
          .and()
            .csrf()
              .disable()
            .formLogin()
              .disable()
            .httpBasic()
              .disable()
            .logout()
              .disable();

    // @formatter:on
  }

  @Configuration
  @ConditionalOnExpression("${platform.security.enabled:false}")
  @RequiredArgsConstructor
  public static class RestAuthorizationFilterConfig {

    private final JwtManager jwtManager;
    private final JwtConfigProperties jwtConfigProperties;
    private final CertificateManager certificateManager;
    private final SecurityContextService securityContextService;

    @Bean
    public FilterRegistrationBean<RestAuthorizationFilter> filterRestAuthorizationBean() {
      FilterRegistrationBean<RestAuthorizationFilter> bean = new FilterRegistrationBean<>(
          new RestAuthorizationFilter(jwtManager, jwtConfigProperties, certificateManager, securityContextService));
      bean.setOrder(Ordered.LOWEST_PRECEDENCE);
      return bean;
    }
  }

}

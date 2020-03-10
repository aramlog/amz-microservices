package com.amz.microservices.common.security.filter;

import com.amz.microservices.common.security.SecurityContextService;
import com.amz.microservices.common.security.config.properties.JwtConfigProperties;
import com.amz.microservices.common.security.manager.CertificateManager;
import com.amz.microservices.common.security.manager.JwtManager;
import com.amz.microservices.common.security.pojo.AccessTokenClaims;
import com.amz.microservices.common.security.pojo.ContextUser;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestAuthorizationFilter extends OncePerRequestFilter {

  private final JwtManager jwtManager;
  private final JwtConfigProperties jwtConfigProperties;
  private final CertificateManager certificateManager;
  private final SecurityContextService securityContextService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    final String accessToken = retrieveAccessToken(request);

    if (StringUtils.isNotBlank(accessToken)) {
      log.info("Successfully parsed JWT token from headers. Trying to authenticate user.");
      setAuthentication(accessToken);
    } else {
      log.info("No JWT token found in the headers for uri {} {}", request.getMethod(), request.getRequestURI());
    }

    try {
      filterChain.doFilter(request, response);
    } finally {
      // Clear request related info after request
      securityContextService.clearAuthentication();
    }
  }

  /**
   * Extract claims from JWT token, build {@link UsernamePasswordAuthenticationToken} and set it into {@link
   * SecurityContextService}.
   *
   * @param authToken provided JWT token from header
   */
  private void setAuthentication(final String authToken) {
    final AccessTokenClaims accessTokenClaims = jwtManager.getValidatedMappedClaimsFromToken(authToken,
        certificateManager.publicKey(), AccessTokenClaims.class);

    final List<GrantedAuthority> authorities = accessTokenClaims.getAuthorities()
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    final ContextUser contextUser = ContextUser.builder()
        .userId(accessTokenClaims.getUserId())
        .userAuthorities(accessTokenClaims.getAuthorities())
        .build();

    final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        contextUser, null, authorities);

    securityContextService.setAuthentication(authenticationToken);

    log.info("User successfully authenticated with userId: {} and roles: {}", contextUser.getUserId(),
        contextUser.getUserAuthorities());
  }

  /**
   * Retrieve Bearer token from headers.
   *
   * @param request incoming request
   * @return JWT token if exists orElse null
   */
  private String retrieveAccessToken(final HttpServletRequest request) {
    final String authBearerToken = Optional.ofNullable(request.getHeader(jwtConfigProperties.getAuthorizationHeader()))
        .orElse(null);

    return StringUtils.trimToNull(StringUtils.substringAfter(authBearerToken,
        jwtConfigProperties.getAuthorizationHeaderPrefix()));
  }

}

package com.amz.microservices.common.security.manager;

import com.amz.microservices.common.object.error.ValidatorErrorCode;
import com.amz.microservices.common.object.exception.ValidationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtManager {

  private static final String INVALID_CLAIMS_MESSAGE = "Invalid claims";

  private final ObjectMapper objectMapper;
  private final JwtBuilder jwtBuilder;
  private final JwtParser jwtParser;
  private final Validator validator;

  public <T> String generateTokenFromMappedClaims(final T mappedClaims, final Key key,
      final SignatureAlgorithm algorithm) {
    final Map<String, Object> claims = getClaimsFromValidatedMappedClaims(mappedClaims);
    return generateToken(claims, key, algorithm);
  }

  public <T> T getValidatedMappedClaimsFromToken(final String token, final Key key, final Class<T> toValueType) {
    try {
      Claims claims = getClaimsFromToken(token, key);
      return getValidatedMappedClaims(claims, toValueType);
    } catch (ExpiredJwtException ex) {
      log.debug("The token has expired", ex);
      throw new ValidationException(ValidatorErrorCode.ACCESS_TOKEN_EXPIRED);
    } catch (JwtException | IllegalStateException ex) {
      log.debug("The token is invalid", ex);
      throw new ValidationException(ValidatorErrorCode.ACCESS_TOKEN_INVALID);
    }
  }

  private Claims getClaimsFromToken(final String token, final Key key) {
    return jwtParser
        .setSigningKey(key)
        .parseClaimsJws(token)
        .getBody();
  }

  private String generateToken(final Map<String, Object> claims, final Key key, final SignatureAlgorithm algorithm) {
    return jwtBuilder
        .setClaims(claims)
        .signWith(algorithm, key)
        .compact();
  }

  private <T> Map<String, Object> getClaimsFromValidatedMappedClaims(final T mappedClaims) {
    validateMappedClaims(mappedClaims);
    return objectMapper.convertValue(mappedClaims, new TypeReference<Map<String, Object>>() {
    });
  }

  private <T> T getValidatedMappedClaims(final Claims claims, final Class<T> toValueType) {
    T mappedClaims = objectMapper.convertValue(claims, toValueType);
    validateMappedClaims(mappedClaims);
    return mappedClaims;
  }

  private <T> void validateMappedClaims(final T claims) {
    Set<ConstraintViolation<T>> violations = validator.validate(claims);
    if (!violations.isEmpty()) {
      violations.forEach(violation -> log.error("{} {}", violation.getPropertyPath(), violation.getMessage()));
      throw new IllegalStateException(INVALID_CLAIMS_MESSAGE);
    }
  }

}


package com.amz.microservices.common.security;

import com.amz.microservices.common.security.pojo.ContextUser;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityContextService {

  public UUID getCurrentUserId() {
    return ((ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
  }

  public ContextUser getCurrentUser() {
    return (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public void setAuthentication(final UsernamePasswordAuthenticationToken auth) {
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  public void clearAuthentication() {
    SecurityContextHolder.clearContext();
  }

}

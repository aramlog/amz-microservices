package com.amz.microservices.common.contract.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FeignAuthorizationInterceptor implements RequestInterceptor {

  private static final String TOKEN_HEADER = "Authorization";

  @Override
  public void apply(RequestTemplate requestTemplate) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

    if (requestAttributes instanceof ServletRequestAttributes) {
      ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;

      HttpServletRequest request = servletRequestAttributes.getRequest();
      if (request.getHeader(TOKEN_HEADER) != null) {
        requestTemplate.header(TOKEN_HEADER, request.getHeader(TOKEN_HEADER));
      }
    }
  }
}

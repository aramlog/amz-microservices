package com.amz.microservices.common.contract.feign;

import com.amz.microservices.common.contract.feign.interceptor.FeignAuthorizationInterceptor;
import feign.RequestInterceptor;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.annotation.PathVariableParameterProcessor;
import org.springframework.cloud.openfeign.annotation.RequestHeaderParameterProcessor;
import org.springframework.cloud.openfeign.annotation.RequestParamParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class FeignClientConfig {

  /**
   * @return the request interceptor for authorization bean
   */
  @Bean
  public RequestInterceptor requestInterceptorAuthorization() {
    return new FeignAuthorizationInterceptor();
  }

  /**
   * @return the spring contract bean
   */
  @Bean
  public SpringMvcContract springContract() {
    List<AnnotatedParameterProcessor> processors = new ArrayList<>();
    processors.add(new PathVariableParameterProcessor());
    processors.add(new RequestParamParameterProcessor());
    processors.add(new RequestHeaderParameterProcessor());
    return new SpringMvcContract(processors);
  }

}

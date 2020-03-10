package com.amz.microservices.common.contract;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Amz client config. This enables the feign client for contract module.
 */
@Configuration
@EnableFeignClients(basePackages = "com.amz.microservices")
public class AmzClientConfig {

}

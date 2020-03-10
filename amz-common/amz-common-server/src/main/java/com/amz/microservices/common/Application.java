package com.amz.microservices.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.amz.microservices")
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).run(args);
  }

}

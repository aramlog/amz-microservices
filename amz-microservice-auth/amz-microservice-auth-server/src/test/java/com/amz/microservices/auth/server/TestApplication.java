package com.amz.microservices.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.amz.microservices")
public class TestApplication {

  public static void main(String[] args) {
    new SpringApplication(TestApplication.class).run(args);
  }

}

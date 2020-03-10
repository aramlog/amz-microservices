package com.amz.microservices.auth.server.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.amz.microservices.auth.server.repository")
@EntityScan("com.amz.microservices.auth.server.entity")
public class JpaConfig {
}

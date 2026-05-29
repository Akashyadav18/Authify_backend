package com.Security.Authify.securityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
    // kuch nahi chahiye andar, sirf annotation kaam karega
}

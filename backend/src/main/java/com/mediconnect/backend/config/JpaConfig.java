package com.mediconnect.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enables Spring Data JPA Auditing so that @CreatedDate and @LastModifiedDate
 * fields on entities are populated automatically.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}

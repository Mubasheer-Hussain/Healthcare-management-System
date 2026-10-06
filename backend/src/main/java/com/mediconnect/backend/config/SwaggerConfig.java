package com.mediconnect.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Swagger UI with:
 * – Project metadata
 * – A JWT Bearer token input field on every secured endpoint
 *
 * Access at: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI mediconnectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MediConnect API")
                        .description("Hospital Management System REST API — Auth, Appointments, Records, Messaging")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("MediConnect Team")
                                .email("team@mediconnect.dev")))
                // Adds a "Authorize" button to Swagger UI for JWT testing
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Paste your JWT token here. Obtain it from POST /api/v1/auth/login")));
    }
}

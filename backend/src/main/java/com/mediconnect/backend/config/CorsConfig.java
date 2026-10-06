package com.mediconnect.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Allows the Vite dev server (http://localhost:5173) and any future
 * production origin to reach the API while still enforcing CORS for
 * unknown origins.
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Origins permitted — add your production domain here when ready
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",   // Vite dev server
                "http://localhost:4173"    // Vite preview
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // Expose Authorization header so the frontend can read it if needed
        config.setExposedHeaders(List.of("Authorization"));

        // Allow cookies / Authorization header to be sent cross-origin
        config.setAllowCredentials(true);

        config.setMaxAge(3600L); // Preflight cache 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}

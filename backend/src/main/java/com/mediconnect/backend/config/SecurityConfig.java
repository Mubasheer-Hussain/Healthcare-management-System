package com.mediconnect.backend.config;

import com.mediconnect.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Central Spring Security configuration.
 *
 * Key decisions:
 * – CSRF disabled (stateless JWT API, no session cookies)
 * – Session policy STATELESS (no HttpSession created)
 * – JWT filter runs BEFORE the standard username/password filter
 * – @PreAuthorize enabled via @EnableMethodSecurity for controller-level RBAC
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // enables @PreAuthorize("hasRole('DOCTOR')") on controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    /** Publicly accessible paths – no JWT token required */
    private static final String[] PUBLIC_PATHS = {
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF – not needed for stateless REST APIs
            .csrf(AbstractHttpConfigurer::disable)

            // Apply CORS configuration from CorsConfig bean
            .cors(cors -> cors.configure(http))

            // Allow H2 console frames (needed for X-Frame-Options in browser)
            .headers(headers -> headers.frameOptions(fo -> fo.sameOrigin()))

            // Session management – stateless (JWT based)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Route authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_PATHS).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // preflight
                .requestMatchers(HttpMethod.GET, "/api/v1/doctors/**").permitAll()
                .anyRequest().authenticated())

            // Register our JWT filter before Spring Security's default auth filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            // Use our custom UserDetailsService for authentication
            .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

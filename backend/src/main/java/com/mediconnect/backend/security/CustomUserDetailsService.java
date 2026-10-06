package com.mediconnect.backend.security;

import com.mediconnect.backend.auth.entity.User;
import com.mediconnect.backend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Bridges Spring Security's authentication mechanism with our JPA User entity.
 * Spring Security calls {@code loadUserByUsername} during login; we look up
 * by email and grant a single authority matching the stored role.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No account found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                // Roles must be prefixed with "ROLE_" for Spring Security's hasRole() to work
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                .build();
    }
}

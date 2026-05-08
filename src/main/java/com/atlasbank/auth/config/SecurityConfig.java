package com.atlasbank.auth.config;

import com.atlasbank.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/health", "/actuator/info",
                                "/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/api/v1/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/accounts/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/transfers/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/cards/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/invoices/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/cards/*/invoices").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/api/v1/cards/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

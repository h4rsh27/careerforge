package com.careerforge.config;

import com.careerforge.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .formLogin(form -> form.disable())

                .httpBasic(basic -> basic.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Allow browser CORS preflight requests
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // Public endpoints
                        .requestMatchers(
                                "/api/users",
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Student endpoints
                                .requestMatchers(
                                        "/api/users/me",
                                        "/api/profile/**",
                                        "/api/skills/**",
                                        "/api/resume/**",
                                        "/api/skill-gap/**",
                                        "/api/career-readiness/**",
                                        "/api/learning-roadmap/**",
                                        "/api/job-recommendations/**",
                                        "/api/interviews/**",
                                        "/api/applications/**",
                                        "/api/ai/**"
                                ).hasRole("STUDENT")

                        // Admin endpoints
                                // Job roles can be viewed by students,
// but only admins can create/update/delete them.
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/job-roles/**"
                                ).hasAnyRole("STUDENT", "ADMIN")

// Admin endpoints
                                .requestMatchers(
                                        "/api/admin/**",
                                        "/api/companies/**",
                                        "/api/job-roles/**",
                                        "/api/job-listings/**"
                                ).hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.filter.ApplicationFilter;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        ApplicationFilter applicationFilter = new ApplicationFilter();
        applicationFilter.setFilterProcessesUrl("/authenticate");
        return http
                .csrf(AbstractHttpConfigurer::disable) // ✅ Disable CSRF for APIs
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // ✅ Properly disable frame options for H2
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ✅ Stateless API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2/**").permitAll() // ✅ Correct for Spring Security 6
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .anyRequest().authenticated())
                .addFilter(applicationFilter)
                .build();
    }
}

package org.example.spring.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/ab", "/api/user").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/api/user/{user_id}/balance", "/api/coin/{coinId}/rate").permitAll()
                        .requestMatchers("/api/signUp", "/api/update").permitAll()
                        .requestMatchers("/api/signIn", "api/files/full").permitAll()
                        .requestMatchers("/api/files/{user_id}", "/api/files", "api/files/temp", "api/files/delete").permitAll()
                        .requestMatchers("/api/history/savings", "/api/history/start/savings", "/api/history/update/savings").permitAll()
                        .anyRequest().authenticated()
                );
                //.httpBasic(withDefaults());

        return http.build();
    }
}
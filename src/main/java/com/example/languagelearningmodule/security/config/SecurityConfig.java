package com.example.languagelearningmodule.security.config;

import com.example.languagelearningmodule.security.CustomUserDetailsService;
import com.example.languagelearningmodule.security.jwt.JwtAuthenticationFilter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService customUserDetailsService;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthFilter)
        {
            this.customUserDetailsService = customUserDetailsService;
            this.jwtAuthFilter = jwtAuthFilter;
        }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/exercises/create-new-exercise").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/exercises/display-exercises/*").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/exercises/update-exercise/*").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/exercises/delete-exercise/*").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/exercises/display-exercises").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/exercises/create-new-lesson").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/exercises/display-lessons/*").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/exercises/update-lesson/*").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/exercises/delete-lesson/*").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/exercises/display-lessons").hasAnyRole("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/api/roles/*").hasRole("ROLE_ADMIN")
                        .requestMatchers("/api/user-progress").authenticated()
                        .requestMatchers("/auth/authenticate").permitAll()
                        .requestMatchers("/api/user/register").permitAll()
                        .requestMatchers("/api/user/display-users/*").authenticated()
                        .requestMatchers("/api/user/update-user/*").authenticated()
                        .requestMatchers("/api/user/delete-user/*").authenticated()
                        .requestMatchers("/api/user/display-users").authenticated()

                        .anyRequest().authenticated()
                );
        // Add JWT filter before UsernamePasswordAuthenticationFilter
        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}

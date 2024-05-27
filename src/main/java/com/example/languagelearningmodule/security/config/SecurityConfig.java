package com.example.languagelearningmodule.security.config;

import com.example.languagelearningmodule.security.jwt.JwtAuthenticationFilter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class SecurityConfig  {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider)
        {
            this.authenticationProvider = authenticationProvider;
            this.jwtAuthFilter = jwtAuthFilter;
        }

   /* @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

       /* httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider);

      */

         httpSecurity.csrf(csrf -> csrf
                        .ignoringRequestMatchers("/auth/**") // Exclude /auth/** from CSRF protection
                       .ignoringRequestMatchers("/api/**")
                )
                        .authorizeHttpRequests((authorize) -> authorize
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/exercises/create-new-exercise").hasRole("ADMIN")
                        .requestMatchers("/api/exercises/view-exercise/*").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/api/exercises/update-exercise/*").hasRole("ADMIN")
                        .requestMatchers("/api/exercises/delete-exercise/*").hasRole("ADMIN")
                        .requestMatchers("/api/exercises/view-exercises").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/api/exercises/create-new-lesson").hasRole("ADMIN")
                        .requestMatchers("/api/exercises/view-lesson/*").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/api/exercises/update-lesson/*").hasRole("ADMIN")
                        .requestMatchers("/api/exercises/delete-lesson/*").hasRole("ADMIN")
                        .requestMatchers("/api/exercises/view-lessons").hasAnyAuthority("ADMIN", "USER")
                        .requestMatchers("/api/roles/**").hasRole("ADMIN")
                        .requestMatchers("/api/user-progress").authenticated()
                        .requestMatchers("/api/user/display-users/*").authenticated()
                        .requestMatchers("/api/user/update-user/*").authenticated()
                        .requestMatchers("/api/user/delete-user/*").authenticated()
                        .requestMatchers("/api/user/display-users").authenticated()
                        .anyRequest().authenticated()
                );


        // Add JWT filter before UsernamePasswordAuthenticationFilter
        if (jwtAuthFilter != null) {
            httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        }
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}

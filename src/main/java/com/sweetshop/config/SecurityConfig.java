package com.sweetshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // for a JSON API we usually disable CSRF (if you use cookies replace this with
                // proper CSRF handling)
            .csrf(csrf -> csrf.disable())
        
            .authorizeHttpRequests(auth -> auth
            // [FIX 1] Explicitly allow the browser's pre-flight checks (OPTIONS requests)
            .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
            
            // [FIX 2] Allow access to the standard error endpoint so you can see REAL errors
            .requestMatchers("/error").permitAll()
            
            // Allow your auth endpoints
            .requestMatchers("/api/auth/**").permitAll()
            
            // All other requests require authentication
            .anyRequest().authenticated())
                // enable CORS so controller-level `@CrossOrigin` is honored and preflight
                // requests are allowed
                .cors(Customizer.withDefaults())
                // stateless session (JWT tokens)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // disable default login forms and HTTP Basic challenge (prevents browser auth
                // popup)
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

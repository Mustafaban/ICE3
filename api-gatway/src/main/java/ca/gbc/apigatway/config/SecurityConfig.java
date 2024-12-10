package ca.gbc.apigatway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String [] noAuthResourceUris = {
            "/swagger-ui",
            "/swagger-ui/*",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "api-docs/**",
            "aggregate/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        log.info("Initializing SecurityFilterChain");

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)          // Disable CSRF protection
                //.authorizeHttpRequests(authorize -> authorize
                //        .anyRequest().permitAll())            // All requests are temporary permitted
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(noAuthResourceUris)
                        .permitAll()
                        .anyRequest().authenticated())          // All requests require authentication
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .build();

    }

}

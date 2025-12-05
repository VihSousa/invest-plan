package br.com.VihSousa.invest_plan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

            // Disable CSRF protection
            .csrf(csrf -> csrf.disable()) 
            
            // Config the authorization rules
            .authorizeHttpRequests(auth -> auth
                // Alows free access to Swagger (so you don't have to log in to see the doc)
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // For any other request, requires login
                .anyRequest().authenticated()
            )
            
            // Enables basic login (that browser/postman popup)
            .httpBasic(withDefaults());

        return http.build();
    }
}
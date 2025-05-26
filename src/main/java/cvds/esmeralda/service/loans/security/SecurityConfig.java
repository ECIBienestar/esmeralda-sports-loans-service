package cvds.esmeralda.service.loans.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Swagger no envía CSRF token
                .authorizeHttpRequests(auth -> auth
                        // 👉 permite acceso libre a Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // 👉 protege el resto
                        .anyRequest().authenticated()
                )
                .httpBasic(); // Habilita autenticación básica

        return http.build();
    }
}

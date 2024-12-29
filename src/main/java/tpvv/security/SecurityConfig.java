package tpvv.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ApiKeyAuthFilter apiKeyAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http)
            throws Exception {

        http
                // Configuración de CSRF:
                // 1. Ignoramos CSRF en /h2-console/** y /pago/**
                // 2. Usamos CookieCsrfTokenRepository para el resto
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/pago/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )

                // Permitir iframes en la misma ruta (necesario para la consola H2)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))

                // Control de sesiones (STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()  // Permitir H2
                        .requestMatchers("/pago/**").authenticated()    // Exigir autenticación en /pago
                        .anyRequest().permitAll()                      // Todo lo demás, libre
                )

                // Filtro personalizado antes de UsernamePasswordAuthenticationFilter
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // Autenticación básica
                .httpBasic(Customizer.withDefaults());

        // Construye el SecurityFilterChain
        return http.build();
    }
}

package prueba.tecnica.consiti.clima.security.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SegurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                {
                    // Endpoints sin autenticación
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/api/clima/**").permitAll();

                    // Endpoints accesibles para ADMIN y USER
                    auth.requestMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("ADMIN", "USER");

                    // Endpoints accesibles solo para ADMIN
                    auth.requestMatchers(HttpMethod.GET, "/usuario/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/usuario/**").hasRole("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .csrf(config -> config.disable()) // SOLO PARA THYMELEAF
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .build();
    }
}

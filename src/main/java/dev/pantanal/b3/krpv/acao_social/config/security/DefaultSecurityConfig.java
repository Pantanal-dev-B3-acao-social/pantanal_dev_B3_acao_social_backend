package dev.pantanal.b3.krpv.acao_social.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class DefaultSecurityConfig {

//    private final JwtAuthConverter jwtAuthConverter;
    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-secret}")
    private String clientSecret;

    @Value("${server.host}")
    private String authServerHost;

    @Value("${server.port}")
    private String authServerPort;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
//        http.authorizeHttpRequests(
//            authorize -> authorize
//                .requestMatchers("/public/**").permitAll()
//                .requestMatchers("/auth/token/user/**").permitAll()
//                .anyRequest().authenticated()
//        );
        http.oauth2ResourceServer(
                    oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new JWTConverter()))
            );
        return http.build();
    }


}
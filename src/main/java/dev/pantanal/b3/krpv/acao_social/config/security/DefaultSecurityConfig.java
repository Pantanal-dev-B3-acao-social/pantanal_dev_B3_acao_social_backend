package dev.pantanal.b3.krpv.acao_social.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
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
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )
//                .rememberMe(Customizer.withDefaults())
        ;
        http
            .oauth2ResourceServer()
            .jwt()
//                .jwtAuthenticationConverter(jwtAuthConverter)
        ;
        http
            .sessionManagement()
            .sessionCreationPolicy(STATELESS);
        return http.build();
    }


}
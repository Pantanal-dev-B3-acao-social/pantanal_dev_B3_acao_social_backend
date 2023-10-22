package dev.pantanal.b3.krpv.acao_social.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import static dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthController.ROUTE_AUTH;
import static dev.pantanal.b3.krpv.acao_social.modulos.ong.OngController.ROUTE_ONG;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class DefaultSecurityConfig {

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-secret}")
    private String clientSecret;

// TODO: apagar
//    @Value("${server.host}")
//    private String authServerHost;

    @Value("${server.port}")
    private String authServerPort;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(
                authorizeConfig -> {
                    authorizeConfig.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorizeConfig.requestMatchers(ROUTE_AUTH+"/login").permitAll();
                    authorizeConfig.requestMatchers( ROUTE_ONG+"/home").permitAll();
                    authorizeConfig.requestMatchers( "/swagger-ui", "/swagger-ui/**", "/swagger-ui/index.html#").permitAll();
                    authorizeConfig.anyRequest().authenticated();
                }
        );

//        http.cors().and().authorizeRequests().requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        http.oauth2ResourceServer(
            oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(new JWTConverter()))
        );
        return http.build();
    }


}
package dev.pantanal.b3.krpv.acao_social.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;

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

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        // mvcMatchers Cannot resolve method 'mvcMatchers' in 'AuthorizationManagerRequestMatcherRegistry'
//                        // antMatchers Cannot resolve method 'antMatchers' in 'AuthorizationManagerRequestMatcherRegistry'
//                        .requestMatchers("/blog/**").permitAll()
//
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .rememberMe(Customizer.withDefaults());
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(
                        authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console-web/**").permitAll() // Permite acesso ao console H2 sem autenticação
//                                .antMatchers("/h2-console-web/**").permitAll() // Permite acesso ao console H2 sem autenticação
                                .anyRequest().authenticated()
                )
        ;
//                .formLogin(Customizer.withDefaults()); // Habilita o formulário de login padrão
//        http
//                .csrf().disable()
//                .headers().frameOptions().disable();
//        http
//                .oauth2ResourceServer()
//                .jwt()
//                .jwtAuthenticationConverter(jwtAuthConverter)
        ;
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(STATELESS);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated();
//        http
//                .oauth2ResourceServer()
//                .jwt()
////                .jwtAuthenticationConverter(jwtAuthConverter)
//        ;
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(STATELESS);
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/blog/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());
//        return http.build();
//    }

}
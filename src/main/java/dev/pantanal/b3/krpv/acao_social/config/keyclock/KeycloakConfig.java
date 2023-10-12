package dev.pantanal.b3.krpv.acao_social.config.keyclock;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${acao-social.keyclock.baseUrl}")
    private String keyclockBaseUrl;

    @Value("${acao-social.keyclock.adminUsername}")
    private String adminUsername;

    @Value("${acao-social.keyclock.adminAassword}")
    private String adminAassword;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.provider}")
    private String realmId;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;


    @Bean
    public Keycloak keycloak() {

        return KeycloakBuilder.builder()
                .serverUrl(keyclockBaseUrl + "/")
                .realm(realmId)
                .grantType(OAuth2Constants.PASSWORD)
//                .username(adminUsername)
//                .password(adminAassword)
                .username("funcionario1")
                .password("123")
                .clientId(clientId)
                .resteasyClient(
                        new ResteasyClientBuilderImpl().connectionPoolSize(10).build()
                ).build();
    }

}

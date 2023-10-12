package dev.pantanal.b3.krpv.acao_social.modulos.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton") // Definindo o escopo como singleton
public class KeycloakClient {

    private String clientToken;

    private long tokenExpirationTime; // Timestamp when the token will expire


    private Map<String, List<String>> attributes; // Mapear atributos personalizados

    @org.springframework.beans.factory.annotation.Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-id}")
    private String clientId;

    @org.springframework.beans.factory.annotation.Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Autowired
    ObjectMapper objectMapper;


    /**
     * Esse token é usado para autenticar e autorizar solicitações a recursos protegidos por um servidor OAuth 2.0 ou OpenID Connect.
     * Quando este backend spring boot precisar fazer request diretamente para o keyclock deve usar o token gerando por esta request
     */
    public synchronized String getClientToken() {
        if (isTokenExpired()) {
            ResponseEntity<String> result = this.getClientTokenFromKeyclock();
            if (result.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode responseJson = objectMapper.readTree(result.getBody());
                    this.clientToken = responseJson.get("access_token").asText();
                    long expiresIn = responseJson.get("expires_in").asLong();
                    this.tokenExpirationTime = System.currentTimeMillis() + (expiresIn * 1000);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse the client token response.", e);
                }
            } else {
                throw new RuntimeException("Failed to obtain a client token from Keycloak.");
            }
        }
        return this.clientToken;
    }

    private boolean isTokenExpired() {
        return System.currentTimeMillis() >= tokenExpirationTime;
        // return System.currentTimeMillis() >= (expirationTimeInMilliseconds - someBuffer);
    }

    private synchronized ResponseEntity<String> getClientTokenFromKeyclock() {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_secret", this.clientSecret);
        formData.add("client_id", this.clientId);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);
        String urlEndpoint = this.issuerUri + "/protocol/openid-connect/token";
         ResponseEntity<String> response = rt.exchange(urlEndpoint, HttpMethod.POST, httpEntity, String.class);
        return response;
    }

}

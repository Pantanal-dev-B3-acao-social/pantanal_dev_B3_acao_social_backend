package dev.pantanal.b3.krpv.acao_social.modulos.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.UUID;

@Service
public class UserService {

    @Value("${acao-social.keyclock.baseUrl}")
    private String keyclockBaseUrl;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.provider}")
    private String realmId;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Autowired
    private KeyclockAuthService authService;

    public ResponseEntity<String> userProfile (UUID userId, JwtAuthenticationToken userLogged ) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/" + userId.toString();
        String accessToken = userLogged.getToken().getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Object> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(urlEndpoint));
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response;
    }

    public KeycloakUser userInfoLogged (UUID userId, JwtAuthenticationToken userLogged) {
        ResponseEntity<String> responseEntity = userProfile(userId, userLogged);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                // Use o ObjectMapper do Jackson para desserializar a resposta JSON na sua classe KeycloakUser
                ObjectMapper objectMapper = new ObjectMapper();
                KeycloakUser keycloakUser = objectMapper.readValue(responseEntity.getBody(), KeycloakUser.class);
                return keycloakUser;
            } catch (Exception e) {
                // Lide com exceções de desserialização ou outras exceções aqui
                e.printStackTrace();
                return null; // Ou lance uma exceção personalizada, se preferir
            }
        } else {
            // Lidar com outros códigos de status, se necessário
            return null;
        }
    }

    /*
     * Atualizar o usuário
     */
//    public ResponseEntity<KeycloakUser> updateUser(UserUpdatedto user) {
//        // put
//        String url = " PUT /admin/realms/{realm}/users/{id}";
//
//        return new KeycloakUser(
//                null, null, null,
//        );
//    }


    /*
    * Envie um e-mail ao usuário com um link no qual ele poderá clicar para redefinir a senha.
    */
//    public ResponseEntity<KeycloakUser> resetPasswordEmail(UserResetPasswordDto dto) {
//        // put
//        String url = "PUT /admin/realms/{realm}/users/{id}/reset-password-email";
//
//        return new KeycloakUser(
//                null, null, null,
//                );
//    }


}

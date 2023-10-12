package dev.pantanal.b3.krpv.acao_social.modulos.user;

import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.UUID;

@Service
public class UserService {

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.provider}")
    private String realmId;

    @Value("${acao-social.keyclock.baseUrl}")
    private String keyclockBaseUrl;

    @Autowired
    KeycloakClient keycloakClient;

    public ResponseEntity<String> findAll(
            /*
        String username,
        String firstName,
        String lastName,
        String email,
        Integer first,
        Integer max
             */
    ) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakClient.getClientToken());
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Object> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(urlEndpoint));
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response;
    }

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

    public ResponseEntity<String> create(UserCreateDto dto) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakClient.getClientToken());
        HttpEntity<UserCreateDto> requestEntity = new HttpEntity<>(dto, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                urlEndpoint,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return response;
    }

    public ResponseEntity<String> update(UserUpdateDto dto) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakClient.getClientToken());
        HttpEntity<UserUpdateDto> requestEntity = new HttpEntity<>(dto, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                urlEndpoint,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );
        return response;
    }

}

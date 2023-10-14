package dev.pantanal.b3.krpv.acao_social.modulos.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.response.SocialActionResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.provider}")
    private String realmId;

    @Value("${acao-social.keyclock.baseUrl}")
    private String keyclockBaseUrl;

    @Autowired
    KeycloakClient keycloakClient;

    @Autowired
    ObjectMapper objectMapper;

    public ResponseEntity<String> findAll(
        Integer page,
        Integer size
            /*
        String username,
        String firstName,
        String lastName,
        String email,
        Integer first,
        Integer max
             */
    ) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users?first="+page+"&max="+size;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakClient.getClientToken());
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Object> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(urlEndpoint));
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        return response;
    }

    public KeycloakUser findById (UUID userId, String tokenUserLogged) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/" + userId.toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenUserLogged);
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Object> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(urlEndpoint)
        );
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        String body = response.getBody();
        try {
            KeycloakUser keycloakUser = objectMapper.readValue(body, KeycloakUser.class);
            return keycloakUser;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao user findById.", e);
        }
    }

    public UUID create(UserCreateDto dto) {
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
        if(response.getStatusCode() == HttpStatus.CREATED) {
            UUID userId = getUserIdByResponse(response);
            return userId;
        }
        return null;
    }

    public ResponseEntity<String> update(UUID id, UserUpdateDto dto) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/" + id;
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

    public ResponseEntity<String> delete(UUID id) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakClient.getClientToken());
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                urlEndpoint,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );
        return response;
    }

    /* extrair o ID do usuário da resposta */
    private UUID getUserIdByResponse(ResponseEntity<String> response) {
        try {
            if (response.getStatusCode() == HttpStatus.CREATED) {
//                String locationHeader = response.headers().firstValue("Location").orElse("");
//                String[] parts = locationHeader.split("/");
//                String userId = parts[parts.length - 1];
                //
                HttpHeaders responseHeaders = response.getHeaders();
                List<String> locationHeader = responseHeaders.get("Location");
                if (locationHeader != null && !locationHeader.isEmpty()) {
                    String locationUrl = locationHeader.get(0);
                    String[] parts = locationUrl.split("/");
                    String userId = parts[parts.length - 1];
                    return UUID.fromString(userId);
                } else {
                    throw new RuntimeException("O cabeçalho 'Location' não foi encontrado na resposta.");
                }
            } else {
                throw new RuntimeException("A solicitação para criar o usuário FALHOU.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário.", e);
        }
    }

    public KeycloakUser profile (UUID userId, JwtAuthenticationToken userLogged) {
        String urlEndpoint = keyclockBaseUrl + "/admin/realms/" + realmId + "/users/" + userId.toString();
        String accessToken = userLogged.getToken().getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Object> request = new RequestEntity<>(
                headers, HttpMethod.GET, URI.create(urlEndpoint)
        );
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        String body = response.getBody();
        try {
            KeycloakUser keycloakUser = objectMapper.readValue(body, KeycloakUser.class);
            return keycloakUser;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao profile usuário.", e);
        }
    }

}

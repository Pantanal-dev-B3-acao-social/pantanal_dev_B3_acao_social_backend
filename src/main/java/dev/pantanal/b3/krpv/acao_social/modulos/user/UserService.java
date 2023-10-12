package dev.pantanal.b3.krpv.acao_social.modulos.user;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.QueryParam;
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
    Keycloak keycloak;

    public UserResource findById(String userId) {
        UserResource userResource = keycloak.realm(realmId).users().get(userId);
        return userResource;
    }

    public List<UserRepresentation> findAll(
        String username,
        String firstName,
        String lastName,
        String email,
        Integer first,
        Integer max
    ) {
        // List<UserRepresentation> searchByEmail​(String email, Boolean exact);
        // List<UserRepresentation> searchByFirstName​(String email, Boolean exact);
        // List<UserRepresentation> searchByLastName​(String email, Boolean exact);
        // List<UserRepresentation> searchByUsername​(String username, Boolean exact);

        List<UserRepresentation> userRepresentationsList = keycloak.realm(realmId).users().searchByEmail(email, true);
        List<UserRepresentation> users = keycloak.realm(realmId).users().searchByUsername(username, true);

        return userRepresentationsList;
    }

    public List<UserRepresentation> findByAttributes (String attr) {
        // TODO
        List<UserRepresentation> users = keycloak.realm(realmId).users().searchByAttributes("phone:" + attr);
        return users;
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

}

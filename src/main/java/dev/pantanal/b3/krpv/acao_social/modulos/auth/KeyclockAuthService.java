package dev.pantanal.b3.krpv.acao_social.modulos.auth;

import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeyclockAuthService {

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.realm-pantanal-dev.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    public ResponseEntity<String> loginUser(LoginUserDto userDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_secret", clientSecret);
        formData.add("client_id", clientId);
        formData.add("username", userDto.username());
        formData.add("password", userDto.password());
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);
        String tokenEndpoint = issuerUri + "/protocol/openid-connect/token";
        RestTemplate rt = new RestTemplate();
        var result = rt.postForEntity(tokenEndpoint, httpEntity, String.class);
        return result;
    }

}

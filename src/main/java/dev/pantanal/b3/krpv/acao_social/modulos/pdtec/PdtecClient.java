package dev.pantanal.b3.krpv.acao_social.modulos.pdtec;

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

@Component
@Scope("singleton")
public class PdtecClient {

    private String loggedToken;

    private long tokenExpirationTime;

    @Value("${acao-social.pdtec.loginUrl}")
    private String loginUrl;

    @Value("${acao-social.pdtec.username}")
    private String username;

    @Value("${acao-social.pdtec.password}")
    private String password;

    @Value("${acao-social.pdtec.client_id}")
    private String clientId;

    @Value("${acao-social.pdtec.grant_type}")
    private String grantType;

    @Autowired
    ObjectMapper objectMapper;

    public synchronized ResponseEntity<String> getAuthToken(){
        HttpHeaders headers = new HttpHeaders();
        RestTemplate rt = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", this.username);
        formData.add("password", this.password);
        formData.add("client_id", this.clientId);
        formData.add("grant_type", this.grantType);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);
        String urlEndpoint = this.loginUrl;
        ResponseEntity<String> response = rt.exchange(urlEndpoint, HttpMethod.POST, httpEntity, String.class);
        return response;
    }

    private boolean isTokenExpired() {
        return System.currentTimeMillis() >= tokenExpirationTime;
    }

    public synchronized String getAccessToken() {
        if (isTokenExpired()) {
            ResponseEntity<String> result = this.getAuthToken();
            if (result.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode responseJson = objectMapper.readTree(result.getBody());
                    this.loggedToken = responseJson.get("access_token").asText();
                    long expiresIn = responseJson.get("expires_in").asLong();
                    this.tokenExpirationTime = System.currentTimeMillis() + (expiresIn * 1000);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse the client token response.", e);
                }
            } else {
                throw new RuntimeException("Failed to obtain a client token from Pdtec.");
            }
        }
        return this.loggedToken;
    }
}

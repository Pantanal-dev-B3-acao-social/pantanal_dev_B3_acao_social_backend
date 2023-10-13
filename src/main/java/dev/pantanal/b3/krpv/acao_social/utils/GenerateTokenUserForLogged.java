package dev.pantanal.b3.krpv.acao_social.utils;

import dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthService;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

@Component
public class GenerateTokenUserForLogged {

    @Autowired
    KeyclockAuthService keyclockAuthService;
    public String loginUserMock(LoginUserDto userDto) {
        String accessToken = "";
        ResponseEntity<String> response = keyclockAuthService.loginUser(userDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            try (JsonReader jsonReader = Json.createReader(new StringReader(response.getBody()))) {
                JsonObject jsonObject = jsonReader.readObject();
                accessToken = jsonObject.getString("access_token");
            } catch (Exception e) {
                throw new RuntimeException("Erro ao analisar a resposta JSON", e);

            }
        } else {
            throw new RuntimeException("Falha na solicitação. Código de status: " + response.getStatusCodeValue());
        }
        return accessToken;
    }


}

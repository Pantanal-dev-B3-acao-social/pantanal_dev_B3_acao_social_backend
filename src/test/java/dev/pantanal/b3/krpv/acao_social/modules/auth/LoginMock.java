package dev.pantanal.b3.krpv.acao_social.modules.auth;

import dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthService;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginMock {

    @Autowired
    KeyclockAuthService keyclockAuthService;
    public String loginUserMock(LoginUserDto userDto) {
        String accessToken = "";
        var result = keyclockAuthService.loginUser(userDto);
        if (result.getStatusCode().is2xxSuccessful()) {
            try {
                JSONObject jsonResponse = new JSONObject(result.getBody());
                if (jsonResponse.has("access_token")) {
                    accessToken = jsonResponse.getString("access_token");
                } else {
                    // TODO: lançar exceção
                    System.err.println("O campo 'access_token' não foi encontrado no JSON.");
                }
            } catch (JSONException e) {
                // TODO: lançar exceção
                System.err.println("Erro ao analisar o JSON: " + e.getMessage());
            }
        } else {
            // TODO: lançar exceção
            System.err.println("A solicitação não foi bem-sucedida. Código de status: " + result.getStatusCodeValue());
        }
        return accessToken;
    }


}

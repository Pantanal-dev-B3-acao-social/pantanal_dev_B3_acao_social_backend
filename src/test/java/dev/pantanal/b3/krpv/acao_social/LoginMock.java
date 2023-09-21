package dev.pantanal.b3.krpv.acao_social;

import dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthService;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthController.ROUTE_AUTH;

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

    public String token(LoginUserDto userDto, MockMvc mockMvc) throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(ROUTE_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \" "+userDto.username()+" \",\"password\": \" "+userDto.username()+" \"}")
        );
        resultActions
            .andExpect(MockMvcResultMatchers.status().isOk())
        ;
        // TODO:
//                .andExpect(header().exists("Authorization"))
//                .andExpect(header().string("Authorization", startsWith("Bearer ")));
        return resultActions.andReturn().getResponse().getHeader("Authorization");
    }
}

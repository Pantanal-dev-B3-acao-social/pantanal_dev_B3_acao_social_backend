package dev.pantanal.b3.krpv.acao_social.modules.auth;


import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthController.ROUTE_AUTH;

@SpringBootTest
@AutoConfigureMockMvc
public class KeyclockAuthControllerIT {


    @Test
    @DisplayName("login do user 'funcionario1' com sucesso, validando seus cargos")
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
        // SOCIAL_ACTION_CREATE
        return resultActions.andReturn().getResponse().getHeader("Authorization");
    }

}

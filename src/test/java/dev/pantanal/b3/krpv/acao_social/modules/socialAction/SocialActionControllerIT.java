package dev.pantanal.b3.krpv.acao_social.modules.socialAction;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.LoginMock;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.SocialActionDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionController.ROUTE_SOCIAL;

@SpringBootTest
@AutoConfigureMockMvc
// TODO: @Transactional
public class SocialActionControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    SocialActionPostgresRepository socialActionPostgresRepository;

    @Autowired
    ObjectMapper mapper;
    @Autowired
    LoginMock loginMock;

    @Autowired
    SocialActionRepository socialActionRepository;
    private String tokenUserLogged;

    @Autowired
    SocialActionFactory socialActionFactory;
    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
    }

    @Test
    @DisplayName("lista paginada de ações sociais")
    void findAllSocialAction() throws Exception {
        // Arrange (Organizar)
        List<SocialActionDto> saved = socialActionFactory.insertMany(3);
// TODO:
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SOCIAL)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
// TODO:
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.content").exists())
//            .andExpect(jsonPath("$.content", hasSize(3)))
        ;
    }

    @Test
    @DisplayName("salva uma nova ação social")
    void saveOneSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionDto socialActionDto = socialActionFactory.makeFake();
        ObjectMapper objectMapper = new ObjectMapper();
        String socialActionJson = objectMapper.writeValueAsString(socialActionDto);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_SOCIAL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(socialActionJson)
        );
        // Assert (Verificar)
        resultActions.andDo(MockMvcResultHandlers.print());
//        String socialActionJson = socialActionDto;
//        mockMvc.perform(
//                MockMvcRequestBuilders.post(ROUTE_SOCIAL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(socialActionJson)
//        );
    }

}

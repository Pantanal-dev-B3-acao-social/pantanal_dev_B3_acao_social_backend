package dev.pantanal.b3.krpv.acao_social.modules.socialAction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.modules.auth.LoginMock;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.SocialActionDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.hasSize;
import java.util.List;
import java.util.UUID;

import static dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionController.ROUTE_SOCIAL;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
//@ActiveProfiles("dbinit")
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
        //limpar tabela social_action
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de ações sociais com sucesso")
    void findAllSocialAction() throws Exception {
        // Arrange (Organizar)
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SOCIAL)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (SocialActionEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()));
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova ação social com sucesso")
    void saveOneSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionDto item = socialActionFactory.makeFake();
        ObjectMapper objectMapper = new ObjectMapper();
        String socialActionJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_SOCIAL)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(socialActionJson)
        );
        // Assert (Verificar)
        resultActions
                // antes verificar se esta vazio
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.description()))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("Busca ação social por ID com sucesso")
    void findByIdSocialAction() throws Exception {
        // Arrange (Organizar)
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3);
        SocialActionEntity item = saved.get(0);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SOCIAL + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Exclui uma ação social com sucesso")
    void deleteSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity savedItem = socialActionFactory.insertOne(socialActionFactory.makeFakeEntity());
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_SOCIAL + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        SocialActionEntity deleted = socialActionRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }

    @Test
    @DisplayName("Atualiza uma ação social com sucesso")
    void updateSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity savedItem = socialActionFactory.insertOne(socialActionFactory.makeFakeEntity());
        // Modifica alguns dados da ação social
        savedItem.setName(savedItem.getName() + "_ATUALIZADO");
        savedItem.setDescription(savedItem.getDescription() + "_ATUALIZADO");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String updatedSocialActionJson = objectMapper.writeValueAsString(savedItem);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_SOCIAL + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSocialActionJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedItem.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedItem.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(savedItem.getDescription()))
                .andDo(MockMvcResultHandlers.print());
    }

}

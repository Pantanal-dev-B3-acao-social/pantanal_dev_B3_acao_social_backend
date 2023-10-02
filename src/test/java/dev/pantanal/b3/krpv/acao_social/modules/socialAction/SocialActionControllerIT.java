package dev.pantanal.b3.krpv.acao_social.modules.socialAction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryGroupFactory;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    SocialActionRepository socialActionRepository;
    private String tokenUserLogged;
    @Autowired
    private CategoryFactory categoryFactory;
    @Autowired
    private SocialActionFactory socialActionFactory;
    @Autowired
    CategoryGroupFactory categoryGroupFactory;
    @Autowired
    GenerateTokenUserForLogged generateTokenUserForLogged;
    @Autowired
    LoginMock loginMock;
    private DateTimeFormatter formatter;

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        loginMock.authenticateWithToken(tokenUserLogged);
        List<CategoryGroupEntity> groupEntities = new ArrayList<>();
        CategoryGroupEntity groupEntity = categoryGroupFactory.makeFakeEntity("social action", "grupo de categorias para usar na AÇÃO SOCIAL");
        CategoryGroupEntity groupSaved = categoryGroupFactory.insertOne(groupEntity);
        groupEntities.add(groupSaved);
        categoryFactory.insertMany(1, groupEntities);
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
    @DisplayName("lista paginada,filtrada e ordenada de ações sociais com sucesso")
    void findAllSocialActionWithFilters() throws Exception {
        // Arrange (Organizar)
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3);
        long versionTestLong = 1;
        // Prepare filters, sorting, and paging parameters
        String filter = saved.get(0).getName(); //check for Filter
        String sort = "ASC";    //Check for Sorting
        int pageNumber = 0;
        int pageSize = 1;

        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SOCIAL)
                        .param("name", filter)
                        .param("sort", sort)
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize))
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );

        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1))) //should match the page size
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(saved.get(0).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(saved.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0]description").value(saved.get(0).getDescription()));

    }

    @Test
    @DisplayName("salva uma nova ação social com sucesso")
    void saveOneSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity item = socialActionFactory.makeFakeEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andDo(MockMvcResultHandlers.print());
//        testar groupCategory
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

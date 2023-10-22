package dev.pantanal.b3.krpv.acao_social.modules.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryGroupFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SessionFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtils;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.session.SessionController.ROUTE_SESSION;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class SessionControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    SessionPostgresRepository sessionPostgresRepository;
    @Autowired
    SessionRepository sessionRepository;
    private String tokenUserLogged;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private CategoryFactory categoryFactory;
    @Autowired
    private SocialActionFactory socialActionFactory;
    @Autowired
    CategoryGroupFactory categoryGroupFactory;
    @Autowired
    GenerateTokenUserForLogged generateTokenUserForLogged;
    List<CategoryEntity> categoriesType;
    List<CategoryEntity> categoriesLevel;
    List<UUID> forCategoryTypeIds;
    List<UUID> forCategoryLevelIds;
    @Autowired
    LoginMock loginMock;
    private DateTimeFormatter formatter;
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        loginMock.authenticateWithToken(tokenUserLogged);
        // SOCIAL ACTION
        List<CategoryEntity> categoriesType = categoryFactory.makeFakeByGroup(2, "social action type", "grupo de categorias para usar no TIPO de ação social");
        List<CategoryEntity> categoriesLevel = categoryFactory.makeFakeByGroup(2, "social action level", "grupo de categorias para usar no NÍVEL de ação social");
        List<SocialActionEntity> socialActionEntities = socialActionFactory.insertMany(3);
        List<CategoryEntity> categoryEntities = categoryFactory.makeFakeByGroup(2, "session", "grupo de categorias para usar na SESSÃO da ação social");
        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formate o LocalDateTime esperado
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de session com sucesso")
    void findAllSession() throws Exception {
        // Arrange (Organizar)
        List<SessionEntity> saved = sessionFactory.insertMany(4);
        // TODO:        item.setCreatedBy(userLoggedId);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SESSION)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)));
        int i = 0;
        for (SessionEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].dateStartTime").value(item.getDateStartTime().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].dateEndTime").value(item.getDateEndTime().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].status").value(item.getStatus().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].visibility").value(item.getVisibility().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").value(item.getCreatedBy().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").value(item.getCreatedDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").value(
                            item.getLastModifiedBy() == null  ?
                                    null : item.getLastModifiedBy().toString())
                    )
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").value(
                            item.getLastModifiedDate() == null ?
                                    null : item.getLastModifiedDate().format(formatter))
                    )
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].socialActionId").value(item.getSocialAction().getId().toString()));
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova session com sucesso")
    void saveOneSession() throws Exception {
        // Arrange (Organizar)
        SessionEntity item = sessionFactory.makeFakeEntity();
        String jsonRequest = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_SESSION)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateStartTime").value(item.getDateStartTime().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateEndTime").value(item.getDateEndTime().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialActionId").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
//        testar groupCategory
    }


    @Test
    @DisplayName("Busca session por ID com sucesso")
    void findByIdSession() throws Exception {
        // Arrange (Organizar)
        List<SessionEntity> saved = sessionFactory.insertMany(3);
        SessionEntity item = saved.get(0);
        // TODO:        item.setCreatedBy(userLoggedId);
        String createdByString = Optional.ofNullable(item.getCreatedBy()).map(UUID::toString).orElse(null);
        String lastModifiedByString = Optional.ofNullable(item.getLastModifiedBy()).map(UUID::toString).orElse(null);
        String deletedByString = Optional.ofNullable(item.getDeletedBy()).map(UUID::toString).orElse(null);
        String deletedDateString = Optional.ofNullable(item.getDeletedDate()).map(LocalDateTime::toString).orElse(null);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SESSION + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateStartTime").value(item.getDateStartTime().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateEndTime").value(item.getDateEndTime().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialActionId").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(createdByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").value(deletedDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").value(deletedByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(
                        item.getLastModifiedBy() == null  ?
                                null : item.getLastModifiedBy().toString())
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(
                        item.getLastModifiedDate() == null ?
                                null : item.getLastModifiedDate().format(formatter))
                );
    }

    @Test
    @DisplayName("(hard-delete) Exclui uma session com sucesso")
    void deleteSession() throws Exception {
        // Arrange (Organizar)
        SessionEntity savedItem = sessionFactory.insertOne(sessionFactory.makeFakeEntity());
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_SESSION + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        SessionEntity deleted = sessionRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }
    // TODO: implementar soft-delete

    @Test
    @DisplayName("Atualiza uma session com sucesso")
    void updateSession() throws Exception {
        // Arrange (Organizar)
        SessionEntity item = sessionFactory.insertOne(sessionFactory.makeFakeEntity());
        // Modifica alguns dados da session
// TODO:        item.setCreatedBy(userLoggedId);
        item.setDescription(item.getDescription() + "_ATUALIZADO");
        LocalDateTime dateStart = item.getDateStartTime().plusHours(2).plusMinutes(40);
        LocalDateTime dateEnd = dateStart.plusHours(2).plusMinutes(0);
        item.setDateStartTime(dateStart);
        item.setDateEndTime(dateEnd);
        StatusEnum statusEnum = new EnumUtils<StatusEnum>().getRandomValueDiff(item.getStatus());
        item.setStatus(statusEnum);
        VisibilityEnum visibilityEnum = new EnumUtils<VisibilityEnum>().getRandomValueDiff(item.getVisibility());
        item.setVisibility(visibilityEnum);
        FindRegisterRandom findRegisterRandom = new FindRegisterRandom<SocialActionEntity>(entityManager);
        List<SocialActionEntity> socialActions = findRegisterRandom.execute("social_action", 1, SocialActionEntity.class);
        item.setSocialAction(socialActions.get(0));
// TODO: quais dados falta modificar para testar?
        String updatedSessionJson = objectMapper.writeValueAsString(item);
        String createdByString = Optional.ofNullable(item.getCreatedBy()).map(UUID::toString).orElse(null);
        String lastModifiedByString = Optional.ofNullable(item.getLastModifiedBy()).map(UUID::toString).orElse(null);
        String deletedByString = Optional.ofNullable(item.getDeletedBy()).map(UUID::toString).orElse(null);
        String deletedDateString = Optional.ofNullable(item.getDeletedDate()).map(LocalDateTime::toString).orElse(null);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_SESSION + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSessionJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateStartTime").value(item.getDateStartTime().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateEndTime").value(item.getDateEndTime().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialActionId").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(createdByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(lastModifiedByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").value(deletedDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").value(deletedByString));
    }
}

package dev.pantanal.b3.krpv.acao_social.modules.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SessionFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import dev.pantanal.b3.krpv.acao_social.modules.auth.LoginMock;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtil;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
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
    ObjectMapper mapper;

    @Autowired
    LoginMock loginMock;

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
    private SocialActionFactory socialActionFactory;

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de session com sucesso")
    void findAllSession() throws Exception {
        // Arrange (Organizar)
        socialActionFactory.insertMany(2);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (SessionEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].time").value(item.getTime()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].status").value(item.getStatus()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].visibility").value(item.getVisibility()))
// TODO:                   .andExpect(MockMvcResultMatchers.jsonPath(userLoggedId.toString()).value(item.getCreatedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").value(item.getCreatedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").value(item.getLastModifiedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").value(item.getCreatedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").value(item.getLastModifiedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").value(item.getDeletedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").value(item.getDeletedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].socialAction").value(item.getSocialAction()));
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova session com sucesso")
    void saveOneSession() throws Exception {
        // Arrange (Organizar)
        socialActionFactory.insertMany(2);
        SessionEntity item = sessionFactory.makeFakeEntity();
// TODO:        item.setCreatedBy(userLoggedId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // registrar o módulo JSR-310
        String jsonRequest = objectMapper.writeValueAsString(item);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String expectedTime = item.getTime().format(formatter); // Formate o LocalDateTime esperado
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value(expectedTime)) // Compare como string formatada
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()));
    }


    @Test
    @DisplayName("Busca session por ID com sucesso")
    void findByIdSession() throws Exception {
        // Arrange (Organizar)
        socialActionFactory.insertMany(2);
        List<SessionEntity> saved = sessionFactory.insertMany(3);
        SessionEntity item = saved.get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formate o LocalDateTime esperado
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value(item.getTime().format(formatter))) // Compare como string formatada
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(createdByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(lastModifiedByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").value(deletedDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").value(deletedByString));
    }

    @Test
    @DisplayName("(hard-delete) Exclui uma session com sucesso")
    void deleteSession() throws Exception {
        // Arrange (Organizar)
        socialActionFactory.insertMany(2);
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
        socialActionFactory.insertMany(2);
        SessionEntity item = sessionFactory.insertOne(sessionFactory.makeFakeEntity());
        // Modifica alguns dados da session
// TODO:        item.setCreatedBy(userLoggedId);
        item.setDescription(item.getDescription() + "_ATUALIZADO");
        LocalDateTime timeUpdated = item.getTime().plusHours(2).plusMinutes(40);
        item.setTime(timeUpdated);
        StatusEnum statusEnum = new EnumUtil<StatusEnum>().getRandomValueDiff(item.getStatus());
        item.setStatus(statusEnum);
        VisibilityEnum visibilityEnum = new EnumUtil<VisibilityEnum>().getRandomValueDiff(item.getVisibility());
        item.setVisibility(visibilityEnum);
        FindRegisterRandom findRegisterRandom = new FindRegisterRandom<SocialActionEntity>(entityManager);
        List<SocialActionEntity> socialActions = findRegisterRandom.execute("social_action", 1, SocialActionEntity.class);
        item.setSocialAction(socialActions.get(0));
// TODO: quais dados falta modificar para testar?
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String updatedSessionJson = objectMapper.writeValueAsString(item);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formate o LocalDateTime esperado
        String createdByString = Optional.ofNullable(item.getCreatedBy()).map(UUID::toString).orElse(null);
        String lastModifiedByString = Optional.ofNullable(item.getLastModifiedBy()).map(UUID::toString).orElse(null);
        String deletedByString = Optional.ofNullable(item.getDeletedBy()).map(UUID::toString).orElse(null);
        String deletedDateString = Optional.ofNullable(item.getDeletedDate()).map(LocalDateTime::toString).orElse(null);
        String expectedTime = item.getTime().format(formatter); // Formate o LocalDateTime esperado
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value(expectedTime)) // Compare como string formatada
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(createdByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(lastModifiedByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").value(deletedDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").value(deletedByString));
    }
}

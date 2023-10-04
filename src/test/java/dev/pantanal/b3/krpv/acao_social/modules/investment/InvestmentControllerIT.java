package dev.pantanal.b3.krpv.acao_social.modules.investment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CompanyFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.InvestmentFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SessionFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import dev.pantanal.b3.krpv.acao_social.modules.auth.LoginMock;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.InvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository.InvestmentRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.InvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository.InvestmentPostgresRepository;
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

import static dev.pantanal.b3.krpv.acao_social.modulos.Investment.InvestmentController.ROUTE_INVESTMENT;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class InvestmentControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    InvestmentPostgresRepository investmentPostgresRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    LoginMock loginMock;

    @Autowired
    InvestmentRepository investmentRepository;

    private String tokenUserLogged;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    InvestmentFactory investmentFactory;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private SocialActionFactory socialActionFactory;
    @Autowired
    private CompanyFactory companyFactory;

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de Investment com sucesso")
    void findAllInvestment() throws Exception {
        // Arrange (Organizar)
        List<InvestmentEntity> saved = investmentFactory.insertMany(4);
        // TODO:        item.setCreatedBy(userLoggedId);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_INVESTMENT)
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)));
        int i = 0;
        for (InvestmentEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].value_money").value(item.getValue_money()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].date").value(item.getDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].motivation").value(item.getMotivation()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedAt").value(item.getApprovedAt()))
// TODO:                   .andExpect(MockMvcResultMatchers.jsonPath(userLoggedId.toString()).value(item.getCreatedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").value(item.getCreatedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").value(item.getLastModifiedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").value(item.getCreatedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").value(item.getLastModifiedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").value(item.getDeletedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").value(item.getDeletedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].socialAction").value(item.getSocialAction()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].company").value(item.getCompany()));
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova session com sucesso")
    void saveOneInvestment() throws Exception {
        // Arrange (Organizar)
        InvestmentEntity item = investmentFactory.makeFakeEntity();
// TODO:        item.setCreatedBy(userLoggedId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // registrar o módulo JSR-310
        String jsonRequest = objectMapper.writeValueAsString(item);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String expectedDate = item.getDate().format(formatter); // Formate o LocalDateTime esperado
        String expectedApprovedAt = item.getApprovedAt().format(formatter);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_INVESTMENT)
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value_money").value(item.getValue_money()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(expectedDate)) // Compare como string formatada
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedAt").value(expectedApprovedAt))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivation").value(item.getMotivation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.company.id").value(item.getCompany().getId().toString()));
    }


    @Test
    @DisplayName("Busca session por ID com sucesso")
    void findByIdSession() throws Exception {
        // Arrange (Organizar)
        List<InvestmentEntity> saved = investmentFactory.insertMany(3);
        InvestmentEntity item = saved.get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formate o LocalDateTime esperado
        // TODO:        item.setCreatedBy(userLoggedId);
        String createdByString = Optional.ofNullable(item.getCreatedBy()).map(UUID::toString).orElse(null);
        String lastModifiedByString = Optional.ofNullable(item.getLastModifiedBy()).map(UUID::toString).orElse(null);
        String deletedByString = Optional.ofNullable(item.getDeletedBy()).map(UUID::toString).orElse(null);
        String deletedDateString = Optional.ofNullable(item.getDeletedDate()).map(LocalDateTime::toString).orElse(null);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_INVESTMENT + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value_money").value(item.getValue_money()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(item.getDate().format(formatter))) // Compare como string formatada
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedAt").value(item.getApprovedAt().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivation").value(item.getMotivation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.company.id").value(item.getCompany().getId().toString()))
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
        InvestmentEntity savedItem = investmentFactory.insertOne(investmentFactory.makeFakeEntity());
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_INVESTMENT + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        InvestmentEntity deleted = investmentRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }
    // TODO: implementar soft-delete

    @Test
    @DisplayName("Atualiza uma session com sucesso")
    void updateSession() throws Exception {
        // Arrange (Organizar)
        InvestmentEntity item = investmentFactory.insertOne(investmentFactory.makeFakeEntity());
        // Modifica alguns dados da session
// TODO:        item.setCreatedBy(userLoggedId);
        item.setValue_money(item.getValue_money() + 100);
        LocalDateTime dateUpdated = item.getDate().plusHours(2).plusMinutes(40);
        LocalDateTime approvedAtUpdated = item.getApprovedAt().plusHours(2).plusMinutes(40);
        item.setDate(dateUpdated);
        item.setApprovedAt(approvedAtUpdated);
        item.setMotivation(item.getMotivation() + "_Atualizado");
// TODO: quais dados falta modificar para testar?
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String updatedSessionJson = objectMapper.writeValueAsString(item); //Suspeito erro
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formate o LocalDateTime esperado

        String createdByString = Optional.ofNullable(item.getCreatedBy()).map(UUID::toString).orElse(null);
        String lastModifiedByString = Optional.ofNullable(item.getLastModifiedBy()).map(UUID::toString).orElse(null);
        String deletedByString = Optional.ofNullable(item.getDeletedBy()).map(UUID::toString).orElse(null);
        String deletedDateString = Optional.ofNullable(item.getDeletedDate()).map(LocalDateTime::toString).orElse(null);
        String expectedDate = item.getDate().format(formatter); // Formate o LocalDateTime esperado
        String expectedApprovedAt = item.getApprovedAt().format(formatter); // Formate o LocalDateTime esperado
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_INVESTMENT + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSessionJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivation").value(item.getMotivation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(expectedDate)) // Compare como string formatada
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedAt").value(expectedApprovedAt)) // Compare como string formatada
                .andExpect(MockMvcResultMatchers.jsonPath("$.value_money").value(item.getValue_money().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.company.id").value(item.getCompany().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(createdByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(lastModifiedByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").value(deletedDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").value(deletedByString));
    }
}

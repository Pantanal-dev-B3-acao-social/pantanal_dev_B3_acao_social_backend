package dev.pantanal.b3.krpv.acao_social.modules.investment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.*;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.InvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.repository.InvestmentRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.repository.InvestmentPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static dev.pantanal.b3.krpv.acao_social.modulos.investment.InvestmentController.ROUTE_INVESTMENT;
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
    @Autowired
    GenerateTokenUserForLogged generateTokenUserForLogged;
    private DateTimeFormatter formatter;
    ObjectMapper objectMapper;
    @Autowired
    private CategoryFactory categoryFactory;
    @Autowired
    private OngFactory ongFactory;
    @Autowired
    private PersonFactory personFactory;
    @Autowired
    private CategoryGroupFactory categoryGroupFactory;
    List<CompanyEntity> companyEntities;
    List<PersonEntity> personEntities;

    @BeforeEach
    public void setup() throws Exception {
        // token de login
        this.tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        this.loginMock.authenticateWithToken(tokenUserLogged);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        // SOCIAL ACTION
        personFactory.insertOne(personFactory.makeFakeEntity(UUID.fromString(userId)));
        ongFactory.insertOne(ongFactory.makeFakeEntity());
        List<CategoryGroupEntity> categoryGroupLevelEntities = new ArrayList<>();
        CategoryGroupEntity levelGroupEntity = categoryGroupFactory.makeFakeEntity("1", "level of social action", null);
        CategoryGroupEntity levelGroupSaved = categoryGroupFactory.insertOne(levelGroupEntity);
        categoryGroupLevelEntities.add(levelGroupSaved);
        List<CategoryEntity> categoryLevels = this.categoryFactory.insertMany(3, categoryGroupLevelEntities);
        List<UUID> categoryLevelsIds = categoryLevels.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());

        List<CategoryGroupEntity> categoryGroupTypesEntities = new ArrayList<>();
        CategoryGroupEntity typeGroupEntity = categoryGroupFactory.makeFakeEntity("social action type", "grupo de categorias para usar no TIPO de ação social", null);
        CategoryGroupEntity typeGroupSaved = categoryGroupFactory.insertOne(typeGroupEntity);
        categoryGroupTypesEntities.add(typeGroupSaved); //As categorias Desse vetor sempre Estarão relacionadas a um grupo especifico de categorias possiveis para uma entidade
        List<CategoryEntity> categoriesTypes = this.categoryFactory.insertMany(6, categoryGroupTypesEntities); // as 6 categorias pertencem a este grupo

        List<UUID> categoriesTypesIds = categoriesTypes.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
        List<SocialActionEntity> socialActionEntities = socialActionFactory.insertMany(3, categoriesTypesIds, categoryLevelsIds);
        // COMPANY
        this.companyEntities = companyFactory.insertMany(2);
        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        // cadastrar persons
        List<UUID> usersIds = new ArrayList<>();
        int amount = 4;
        for(int i = 0; i < amount; i++) {
            usersIds.add(UUID.randomUUID());
        }
        this.personEntities = personFactory.insertMany(usersIds.size(), usersIds);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de Investment com sucesso")
    void findAllInvestment() throws Exception {
        // Arrange (Organizar)
        List<InvestmentEntity> saved = investmentFactory.insertMany(4);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].valueMoney").value(item.getValueMoney()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].date").value(item.getDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].motivation").value(item.getMotivation()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedBy.id").value(item.getApprovedBy().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedDate").value(item.getApprovedDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").value(item.getCreatedBy().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").value(item.getCreatedDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").value(item.getDeletedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").value(item.getDeletedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].socialAction.id").value(item.getSocialAction().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].company.id").value(item.getCompany().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").value(
                            item.getLastModifiedBy() == null  ?
                                    null : item.getLastModifiedBy().toString())
                    )
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").value(
                            item.getLastModifiedDate() == null ?
                                    null : item.getLastModifiedDate().format(formatter))
                    );
            i++;
        }
    }

    @Test
    @DisplayName("salva um novo Investment com sucesso")
    void saveOneInvestment() throws Exception {
        // Arrange (Organizar)
        InvestmentEntity item = investmentFactory.makeFakeEntity();
        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("valueMoney", item.getValueMoney());
        makeBody.put("date", item.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        makeBody.put("motivation", item.getMotivation());
        makeBody.put("approvedBy", item.getApprovedBy().getId());
        makeBody.put("approvedDate", item.getApprovedDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        makeBody.put("socialAction", item.getSocialAction().getId());
        makeBody.put("company", item.getCompany().getId());
        String jsonRequest = objectMapper.writeValueAsString(makeBody);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueMoney").value(item.getValueMoney()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(item.getDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueMoney").value(item.getValueMoney()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(item.getDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivation").value(item.getMotivation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.company.id").value(item.getCompany().getId().toString()))
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
    @DisplayName("Exclui uma session com sucesso")
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
        item.setValueMoney(item.getValueMoney().add(item.getValueMoney().add(new BigDecimal("5.74"))));
        LocalDateTime dateUpdated = item.getDate().plusHours(2).plusMinutes(40);
        LocalDateTime approvedAtUpdated = item.getApprovedDate().plusHours(2).plusMinutes(40);
        item.setDate(dateUpdated);
        item.setApprovedDate(approvedAtUpdated);
        item.setMotivation(item.getMotivation() + "_Atualizado");
// TODO: quais dados falta modificar para testar?
        String updatedSessionJson = objectMapper.writeValueAsString(item); // TODO: Suspeito erro
        // TODO: nao utilizado
//        String createdByString = Optional.ofNullable(item.getCreatedBy()).map(UUID::toString).orElse(null);
        String lastModifiedByString = Optional.ofNullable(item.getLastModifiedBy()).map(UUID::toString).orElse(null);
        String deletedByString = Optional.ofNullable(item.getDeletedBy()).map(UUID::toString).orElse(null);
        String deletedDateString = Optional.ofNullable(item.getDeletedDate()).map(LocalDateTime::toString).orElse(null);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(item.getDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyId").value(item.getCompany().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialActionId").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
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
}

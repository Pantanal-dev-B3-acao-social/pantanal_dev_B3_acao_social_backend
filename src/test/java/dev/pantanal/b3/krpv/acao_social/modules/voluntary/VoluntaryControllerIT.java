package dev.pantanal.b3.krpv.acao_social.modules.voluntary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.*;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import static dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryController.ROUTE_VOLUNTARY;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.repository.VoluntaryRepository;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtils;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class VoluntaryControllerIT {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GenerateTokenUserForLogged generateTokenUserForLogged;
    @Autowired
    LoginMock loginMock;
    private DateTimeFormatter formatter;
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    private String tokenUserLogged;
    @Autowired
    private VoluntaryFactory voluntaryFactory;
    @Autowired
    private VoluntaryRepository voluntaryRepository;
    @Autowired
    private CategoryFactory categoryFactory;
    @Autowired
    private SocialActionFactory socialActionFactory;
    @Autowired
    private OngFactory ongFactory;
    @Autowired
    CategoryGroupFactory categoryGroupFactory;
    @Autowired
    PersonFactory personFactory;
    List<UUID> usersIds = new ArrayList<>();

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        loginMock.authenticateWithToken(tokenUserLogged);
        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formate o LocalDateTime esperado
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        // cadastrar persons
        int amount = 4;
        for(int i = 0; i < amount; i++) {
            usersIds.add(UUID.randomUUID());
        }
        List<PersonEntity> personEntities = personFactory.insertMany(3, usersIds);
        OngEntity ong = ongFactory.insertOne(ongFactory.makeFakeEntity());
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
        // cadastrar socialActons
        List<SocialActionEntity> socialActionEntities = socialActionFactory.insertMany(3, categoriesTypesIds, categoryLevelsIds);

    }

    @Test
    @DisplayName("lista paginada de voluntary com sucesso")
    void findAllVoluntary() throws Exception {
        // Arrange (Organizar)
        List<VoluntaryEntity> saved = voluntaryFactory.insertMany(4);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_VOLUNTARY)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)));
        int i = 0;
        for (VoluntaryEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].observation").value(item.getObservation()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].socialAction.id").value(item.getSocialAction().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].person.id").value(item.getPerson().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].status").value(item.getStatus().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedBy.id").value(item.getApprovedBy().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedDate").value(item.getApprovedDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].feedbackScoreVoluntary").value(item.getFeedbackScoreVoluntary()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].feedbackVoluntary").value(item.getFeedbackVoluntary()))
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
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").isEmpty());
            i++;
        }
    }


    @Test
    @DisplayName("salva uma nova voluntary com sucesso")
    void saveOneVoluntary() throws Exception {
        // Arrange (Organizar)
        VoluntaryEntity item = voluntaryFactory.makeFakeEntity();
        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("observation", item.getObservation());
        makeBody.put("person", item.getPerson().getId().toString());
        makeBody.put("approvedBy", item.getApprovedBy().getId().toString());
        makeBody.put("approvedDate", item.getApprovedDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        makeBody.put("status", item.getStatus());
        makeBody.put("feedbackScoreVoluntary", item.getFeedbackScoreVoluntary());
        makeBody.put("feedbackVoluntary", item.getFeedbackVoluntary());
        makeBody.put("socialAction", item.getSocialAction().getId());
        String jsonRequest = objectMapper.writeValueAsString(makeBody);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_VOLUNTARY)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.observation").value(item.getObservation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.id").value(item.getPerson().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.feedbackVoluntary").value(item.getFeedbackVoluntary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.feedbackScoreVoluntary").value(item.getFeedbackScoreVoluntary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
//        testar groupCategory
    }


    @Test
    @DisplayName("Busca voluntary por ID com sucesso")
    void findByIdVoluntary() throws Exception {
        // Arrange (Organizar)
        List<VoluntaryEntity> saved = voluntaryFactory.insertMany(3);
        VoluntaryEntity item = saved.get(0);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_VOLUNTARY + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.observation").value(item.getObservation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialAction.id").value(item.getSocialAction().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.id").value(item.getPerson().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.feedbackVoluntary").value(item.getFeedbackVoluntary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.feedbackScoreVoluntary").value(item.getFeedbackScoreVoluntary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty())
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
    @DisplayName("Exclui uma voluntary com sucesso")
    void deleteVoluntary() throws Exception {
        // Arrange (Organizar)
        VoluntaryEntity savedItem = voluntaryFactory.insertOne(voluntaryFactory.makeFakeEntity());
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_VOLUNTARY + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        VoluntaryEntity deleted = voluntaryRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }
    // TODO: implementar soft-delete

    @Test
    @DisplayName("Atualiza uma voluntary com sucesso")
    void updateVoluntary() throws Exception {
        // Arrange (Organizar)
        VoluntaryEntity item = voluntaryFactory.insertOne(voluntaryFactory.makeFakeEntity());
        // Modifica alguns dados da voluntary
// TODO:        item.setCreatedBy(userLoggedId);
        LocalDateTime approvedDateUpdate = item.getApprovedDate().minusHours(2).plusMinutes(40);
        FindRegisterRandom<PersonEntity> findPersonRandom = new FindRegisterRandom<PersonEntity>(entityManager);
        List<PersonEntity> persons = findPersonRandom.execute("person", 1, PersonEntity.class);
        StatusEnum statusEnum = new EnumUtils<StatusEnum>().getRandomValueDiff(item.getStatus());

        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("observation", item.getObservation() + "_ATUALIZADO");
        makeBody.put("approvedDate", approvedDateUpdate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        makeBody.put("approvedBy", persons.get(0).getId());
        makeBody.put("status", statusEnum);

        String jsonRequest = objectMapper.writeValueAsString(makeBody);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_VOLUNTARY + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.observation").value(item.getObservation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.id").value(item.getPerson().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.feedbackVoluntary").value(item.getFeedbackVoluntary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.feedbackScoreVoluntary").value(item.getFeedbackScoreVoluntary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty())
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

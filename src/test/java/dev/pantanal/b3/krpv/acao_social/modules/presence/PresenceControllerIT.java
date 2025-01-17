package dev.pantanal.b3.krpv.acao_social.modules.presence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.*;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.repository.PresenceRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceController.ROUTE_PRESENCE;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class PresenceControllerIT {

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
    private PresenceFactory presenceFactory;
    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private CategoryFactory categoryFactory;
    @Autowired
    private SocialActionFactory socialActionFactory;
    @Autowired
    CategoryGroupFactory categoryGroupFactory;
    @Autowired
    PersonFactory personFactory;
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    OngFactory ongFactory;
    List<UUID> usersIds = new ArrayList<>();
    List<SocialActionEntity> socialActionEntities;
    List<PersonEntity> personEntities;
    List<SessionEntity> sessionsEntities;
    private static final Random random = new Random();

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        loginMock.authenticateWithToken(tokenUserLogged);
        loginMock.authenticateWithToken(tokenUserLogged);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formate o LocalDateTime esperado
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        // cadastrar persons
        List<UUID> usersRandom = IntStream.range(0, 30)
                .mapToObj(i -> UUID.randomUUID())
                .collect(Collectors.toList());

        this.personEntities = personFactory.insertMany(usersRandom.size(), usersRandom);

        // cadastrar socialActons

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
        // cadastrar socialActons
        List<SocialActionEntity> socialActionEntities = socialActionFactory.insertMany(3, categoriesTypesIds, categoryLevelsIds);

        // cadastra session
        this.sessionsEntities = this.sessionFactory.insertMany(100);

    }

    @Test
    @DisplayName("lista paginada de presence com sucesso")
    void findAllPresence() throws Exception {
        // Arrange (Organizar)
        List<PresenceEntity> saved = presenceFactory.insertMany(4, personEntities, sessionsEntities, personEntities);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_PRESENCE)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)));
        int i = 0;
        for (PresenceEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].person.id").value(item.getPerson().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].session.id").value(item.getSession().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedBy.id").value(item.getApprovedBy().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedDate").value(item.getApprovedDate().format(formatter)))
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
    @DisplayName("salva uma nova presence com sucesso")
    void saveOnePresence() throws Exception {
        // Arrange (Organizar)
        Integer indexPerson = random.nextInt(0, personEntities.size());
        Integer indexSession = random.nextInt(0, sessionsEntities.size());
        PresenceEntity item = presenceFactory.insertOne(presenceFactory.makeFakeEntity(
                personEntities.get(indexPerson),
                sessionsEntities.get(indexSession),
                personEntities.get(indexPerson)
            )
        );
        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("person", item.getPerson().getId().toString());
        makeBody.put("session", item.getSession().getId().toString());
        makeBody.put("approvedBy", item.getApprovedBy().getId().toString());
        makeBody.put("approvedDate", item.getApprovedDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        String jsonRequest = objectMapper.writeValueAsString(makeBody);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_PRESENCE)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.session.id").value(item.getSession().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.id").value(item.getPerson().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
//        testar groupCategory
    }


    @Test
    @DisplayName("Busca presence por ID com sucesso")
    void findByIdPresence() throws Exception {
        // Arrange (Organizar)
        List<PresenceEntity> saved = presenceFactory.insertMany(3, personEntities, sessionsEntities, personEntities);
        PresenceEntity item = saved.get(0);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_PRESENCE + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.session.id").value(item.getSession().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.person.id").value(item.getPerson().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
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
    @DisplayName("Exclui uma presence com sucesso")
    void deletePresence() throws Exception {
        // Arrange (Organizar)
        Integer indexPerson = random.nextInt(0, personEntities.size());
        Integer indexSession = random.nextInt(0, sessionsEntities.size());
        PresenceEntity item = presenceFactory.makeFakeEntity(
                personEntities.get(indexPerson),
                sessionsEntities.get(indexSession),
                personEntities.get(indexPerson)
        );
        PresenceEntity savedItem = presenceFactory.insertOne(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_PRESENCE + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        PresenceEntity deleted = presenceRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }

}

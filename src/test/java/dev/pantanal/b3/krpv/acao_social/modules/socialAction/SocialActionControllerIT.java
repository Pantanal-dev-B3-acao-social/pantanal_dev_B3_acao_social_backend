package dev.pantanal.b3.krpv.acao_social.modules.socialAction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryGroupFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategorySocialActionTypeFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategorySocialActionTypePostgresRepository;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionController.ROUTE_SOCIAL;
import com.fasterxml.jackson.databind.JsonNode;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
//@ActiveProfiles("dbinit")
public class SocialActionControllerIT {
    @Autowired
    MockMvc mockMvc;
    private static final Random random = new Random();
    @Autowired
    SocialActionPostgresRepository socialActionPostgresRepository;
    @Autowired
    private CategorySocialActionTypeFactory categorySocialActionTypeFactory;
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
//    List<CategorySocialActionTypeEntity> categorySocialActionTypeEntities;
    List<CategoryEntity> categoriesType;
    List<CategoryEntity> categoriesLevel;
    ObjectMapper objectMapper;
    @Autowired
    CategorySocialActionTypePostgresRepository categorySocialActionTypePostgresRepository;
    List<UUID> forCategoryTypeIds;
    List<UUID> forCategoryLevelIds;

    @BeforeEach
    public void setup() throws Exception {
        // token de login
        this.tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        this.loginMock.authenticateWithToken(tokenUserLogged);
        List<CategoryGroupEntity> typesGroupEntities = new ArrayList<>();
        // cria grupo de categoria e categorias para TIPO
        CategoryGroupEntity typeGroupEntity = categoryGroupFactory.makeFakeEntity("social action type", "grupo de categorias para usar no TIPO de ação social");
        CategoryGroupEntity typeGroupSaved = categoryGroupFactory.insertOne(typeGroupEntity);
        typesGroupEntities.add(typeGroupSaved);
        this.categoriesType = categoryFactory.insertMany(6, typesGroupEntities);
        this.forCategoryTypeIds = this.categoriesType.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
        // cria grupo de categoria e categorias para LEVEL
        List<CategoryGroupEntity> levelGroupEntities = new ArrayList<>();
        CategoryGroupEntity levelGroupEntity = categoryGroupFactory.makeFakeEntity("social action level", "grupo de categorias para usar no NÍVEL de ação social");
        CategoryGroupEntity levelGroupSaved = categoryGroupFactory.insertOne(typeGroupEntity);
        levelGroupEntities.add(levelGroupSaved);
        this.categoriesLevel = categoryFactory.insertMany(6, levelGroupEntities);
        this.forCategoryLevelIds = this.categoriesLevel.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());

        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de ações sociais com sucesso")
    void findAllSocialAction() throws Exception {
        // Arrange (Organizar)
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3, forCategoryTypeIds, forCategoryLevelIds);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SOCIAL)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String jsonResponse = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        List<UUID> categoryLevelIds = null;
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(saved.size())));
        int i = 0;
        for (SocialActionEntity item : saved) {
            String socialActionId = jsonNode.at("/content/" + i + "/id").asText();
            assert socialActionId != null && !socialActionId.isEmpty() : "O nó 'propertyName' não pode ser nulo.";
            List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionEntityId(
                    UUID.fromString(socialActionId)
            );
            List<String> categoryTypeIds = categoryTypesEntities.stream()
                    .map(categoryLevel -> categoryLevel.getId().toString())
                    .collect(Collectors.toList());
            resultActions
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categorySocialActionTypeEntities").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categorySocialActionTypeEntities", hasSize(categoryTypeIds.size())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categorySocialActionTypeEntities", containsInAnyOrder(categoryTypeIds.toArray())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categorySocialActionTypeEntities").value(categoryTypeIds))
                    // TODO: criar relacionamento
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categorySocialActionLevelEntities").value(categoryLevelIds))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").value(item.getCreatedBy().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").value(
                            item.getLastModifiedBy() == null  ?
                                    null : item.getLastModifiedBy().toString())
                    )
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").value(
                            item.getLastModifiedDate() == null ?
                                    null : item.getLastModifiedDate().format(formatter))
                    )
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").value(item.getCreatedDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").isEmpty());
            i++;
        }
    }

    @Test
    @DisplayName("lista paginada, filtrada e ordenada de ações sociais com sucesso")
    void findAllSocialActionWithFilters() throws Exception {
        // Arrange (Organizar)
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3, forCategoryTypeIds, forCategoryLevelIds);
        // Prepare filters, sorting, and paging parameters
        String filter = saved.get(0).getName(); //check for Filter
        String sort = "ASC";    //Check for Sorting
        int pageNumber = 0;
        int pageSize = 1;
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SOCIAL)
                        .param("name", filter)
                        .param("sort", sort)
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize))
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String jsonResponse = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
        int i = 0;
        for (SocialActionEntity item : saved) {
            List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionEntityId(item.getId());
            List<String> categoryTypeIds = item.getCategorySocialActionTypeEntities().stream()
                    .map(categoryLevel -> categoryLevel.getId().toString())
                    .collect(Collectors.toList());
            List<UUID> categoryLevelIds = null;
            resultActions
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryTypeIds").isArray())
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryTypeIds", hasSize(categoryTypeIds.size()) ))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryTypeIds", containsInAnyOrder(categoryTypeIds.toArray())))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryLevelIds").value(categoryLevelIds))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").value(item.getCreatedBy().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").value(item.getLastModifiedBy().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").value(item.getCreatedDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").isEmpty());

//            Assert.assertEquals(categoryTypesEntities.get(i).getId(), item.getId());
            i++;
        }

    }

    @Test
    @DisplayName("salva uma nova ação social com sucesso")
    void saveOneSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity item = socialActionFactory.makeFakeEntity(new ArrayList<UUID>(), new ArrayList<UUID>());
        List<UUID> forCategoryTypeIds = categoriesType.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("name", item.getName());
        makeBody.put("description", item.getDescription());
        makeBody.put("version", item.getVersion());
        makeBody.put("categoryTypeIds", forCategoryTypeIds);
        String jsonRequest = objectMapper.writeValueAsString(makeBody);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_SOCIAL)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
        );
        // Assert (Verificar)
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String jsonResponse = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String socialActionId = jsonNode.get("id").asText();
        List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionEntityId(
                UUID.fromString(socialActionId)
        );
        List<String> categoryTypeIds = categoryTypesEntities.stream()
                .map(categoryLevel -> categoryLevel.getId().toString())
                .collect(Collectors.toList());
        List<UUID> categoryLevelIds = null;
        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds", hasSize(categoryTypeIds.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds", containsInAnyOrder(categoryTypeIds.toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryLevelIds").value(categoryLevelIds))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(loginMock.extractUserIdFromJwt(tokenUserLogged)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
//        testar groupCategory
    }


    @Test
    @DisplayName("Busca ação social por ID com sucesso")
    void findByIdSocialAction() throws Exception {
        // Arrange (Organizar)
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3, forCategoryTypeIds, forCategoryLevelIds);
        SocialActionEntity item = saved.get(0);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_SOCIAL + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String jsonResponse = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String socialActionId = jsonNode.get("id").asText();
        List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionEntityId(
                UUID.fromString(socialActionId)
        );
        List<String> categoryTypeIds = categoryTypesEntities.stream()
                .map(categoryLevel -> categoryLevel.getId().toString())
                .collect(Collectors.toList());
        List<UUID> categoryLevelIds = null;
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds", hasSize(categoryTypeIds.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds", containsInAnyOrder(categoryTypeIds.toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryLevelIds").value(categoryLevelIds))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(
                        item.getLastModifiedBy() == null  ?
                                null : item.getLastModifiedBy().toString())
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(this.formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(
                        item.getLastModifiedDate() == null ?
                                null : item.getLastModifiedDate().format(formatter))
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }

    @Test
    @DisplayName("Exclui uma ação social com sucesso")
    void deleteSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity item = socialActionFactory.insertOne(socialActionFactory.makeFakeEntity(forCategoryTypeIds, forCategoryLevelIds));
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_SOCIAL + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        SocialActionEntity deleted = socialActionRepository.findById(item.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }

    @Test
    @DisplayName("Atualiza uma ação social com sucesso")
    void updateSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity item = socialActionFactory.insertOne(socialActionFactory.makeFakeEntity(forCategoryTypeIds, forCategoryLevelIds));
        int halfSize = forCategoryTypeIds.size() / 2;
        List<UUID> forUpdateCategoryTypeIds = new ArrayList<>(forCategoryTypeIds.subList(0, halfSize));
        // Modifica alguns dados da ação social

        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("name", item.getName() + "_ATUALIZADO");
        makeBody.put("description", item.getDescription() + "_ATUALIZADO");
        makeBody.put("version", item.getVersion());
        makeBody.put("categoryTypeIds", forUpdateCategoryTypeIds);
        String jsonRequest = objectMapper.writeValueAsString(makeBody);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_SOCIAL + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        );
        // Assert (Verificar)
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String jsonResponse = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        String socialActionId = jsonNode.get("id").asText();
        List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionEntityId(
                UUID.fromString(socialActionId)
        );
        List<String> categoryTypeIds = categoryTypesEntities.stream()
                .map(categoryLevel -> categoryLevel.getId().toString())
                .collect(Collectors.toList());
        List<UUID> categoryLevelIds = null;
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds", hasSize(categoryTypeIds.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryTypeIds", containsInAnyOrder(categoryTypeIds.toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryLevelIds").value(categoryLevelIds))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(loginMock.extractUserIdFromJwt(tokenUserLogged)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }

}

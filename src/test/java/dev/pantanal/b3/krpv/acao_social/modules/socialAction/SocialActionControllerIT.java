package dev.pantanal.b3.krpv.acao_social.modules.socialAction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.*;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionLevelEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategorySocialActionLevelPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategorySocialActionTypePostgresRepository;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    List<CategoryEntity> categoriesType;
    List<CategoryEntity> categoriesLevel;
    ObjectMapper objectMapper;
    @Autowired
    CategorySocialActionTypePostgresRepository categorySocialActionTypePostgresRepository;
    @Autowired
    CategorySocialActionLevelPostgresRepository categorySocialActionLevelPostgresRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OngFactory ongFactory;
    @Autowired
            PersonFactory personFactory;
    List<UUID> categoriesTypesIds;
    List<UUID> categoryLevelsIds;

    @BeforeEach
    public void setup() throws Exception {
        // token de login
        this.tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        this.loginMock.authenticateWithToken(tokenUserLogged);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        personFactory.insertOne(personFactory.makeFakeEntity(UUID.fromString(userId)));
        ongFactory.insertOne(ongFactory.makeFakeEntity());
        List<CategoryGroupEntity> categoryGroupLevelEntities = new ArrayList<>();
        CategoryGroupEntity levelGroupEntity = categoryGroupFactory.makeFakeEntity("1", "level of social action", null);
        CategoryGroupEntity levelGroupSaved = categoryGroupFactory.insertOne(levelGroupEntity);
        categoryGroupLevelEntities.add(levelGroupSaved);
        List<CategoryEntity> categoryLevels = this.categoryFactory.insertMany(3, categoryGroupLevelEntities);
        this.categoryLevelsIds = categoryLevels.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());

        List<CategoryGroupEntity> categoryGroupTypesEntities = new ArrayList<>();
        CategoryGroupEntity typeGroupEntity = categoryGroupFactory.makeFakeEntity("social action type", "grupo de categorias para usar no TIPO de ação social", null);
        CategoryGroupEntity typeGroupSaved = categoryGroupFactory.insertOne(typeGroupEntity);
        categoryGroupTypesEntities.add(typeGroupSaved); //As categorias Desse vetor sempre Estarão relacionadas a um grupo especifico de categorias possiveis para uma entidade
        List<CategoryEntity> categoriesTypes = this.categoryFactory.insertMany(6, categoryGroupTypesEntities); // as 6 categorias pertencem a este grupo

        this.categoriesTypesIds = categoriesTypes.stream()
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
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3, categoriesTypesIds, categoryLevelsIds);
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
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(saved.size())));
        int i = 0;
        for (SocialActionEntity item : saved) {
            String socialActionId = jsonNode.at("/content/" + i + "/id").asText();
            assert socialActionId != null && !socialActionId.isEmpty() : "O nó 'propertyName' não pode ser nulo.";
            resultActions
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryType").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryLevel").isArray())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].ong.id").value(item.getOng().getId().toString()))
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
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3, categoriesTypesIds, categoryLevelsIds);
        // Prepare filters, sorting, and paging parameters
        String filter = saved.get(0).getName(); //check for Filter
        String sort = "ASC";    //Check for Sorting
        int pageNumber = 0;
        // TODO: Kaio realizar troca de comparação para extrair do pageable do response o pageSize
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
        JsonNode contentArray = jsonNode.get("content");
        if (contentArray.isArray()) {
            for (JsonNode element : contentArray) {
                Assertions.assertEquals(element.get("id").asText(), saved.get(i).getId().toString());
                Assertions.assertEquals(element.get("name").asText(), saved.get(i).getName());
                Assertions.assertEquals(element.get("description").asText(), saved.get(i).getDescription());
                Assertions.assertFalse(element.get("createdBy").asText().isEmpty());
                Assertions.assertFalse(element.get("createdDate").asText().isEmpty());
                Assertions.assertEquals(element.get("createdBy").asText(), saved.get(i).getCreatedBy().toString());
                Assertions.assertEquals(element.get("createdDate").asText(), saved.get(i).getCreatedDate().format(formatter));
                Assertions.assertTrue(element.get("deletedDate").isNull());
                Assertions.assertTrue(element.get("deletedBy").isNull());
                JsonNode jsonLastModifiedBy = element.get("lastModifiedBy");
                Assertions.assertEquals(jsonLastModifiedBy.isNull() ? null : jsonLastModifiedBy.asText(),
                        saved.get(i).getLastModifiedBy() == null  ? null : saved.get(i).getLastModifiedBy().toString()
                );
                JsonNode jsonLastModifiedDate = element.get("lastModifiedDate");
                Assertions.assertEquals(jsonLastModifiedDate.isNull() ? null : jsonLastModifiedDate.asText(),
                        saved.get(i).getLastModifiedDate() == null ? null : saved.get(i).getLastModifiedDate().format(formatter)
                );
                Assertions.assertFalse(element.get("categoryType").isEmpty());
                Assertions.assertFalse(element.get("categoryLevel").isEmpty());
                int i_type = 0;
                JsonNode jsonCategoryType = element.get("categoryType");
                Assertions.assertTrue(jsonCategoryType.isArray());
                List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionId(saved.get(i).getId());
                Assertions.assertEquals(jsonCategoryType.size(), categoryTypesEntities.size());
                for (JsonNode jsonType : jsonCategoryType) {
                    String jsonId = jsonType.get("id").asText();
                    String saveId = categoryTypesEntities.get(i_type).getId().toString();
                    Assertions.assertEquals(jsonId, saveId);
                    i_type++;
                }
                i++;
            }
        }
    }

    @Test
    @DisplayName("salva uma nova ação social com sucesso")
    void saveOneSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity item = socialActionFactory.makeFakeEntity(categoriesTypesIds, categoryLevelsIds);
        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("name", item.getName());
        makeBody.put("description", item.getDescription());
        makeBody.put("version", item.getVersion());
        makeBody.put("ongEntity", item.getOng().getId().toString());
        makeBody.put("categoryType", categoriesTypesIds.subList(0, categoriesTypesIds.size() / 2));
        makeBody.put("categoryLevel", categoryLevelsIds.subList(0, categoryLevelsIds.size() / 2));
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
        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(loginMock.extractUserIdFromJwt(tokenUserLogged)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
        Assertions.assertFalse(jsonNode.get("categoryType").isNull());
        int i_type = 0;
        JsonNode jsonCategoryType = jsonNode.get("categoryType");
        Assertions.assertTrue(jsonCategoryType.isArray());
        UUID socialId = UUID.fromString(jsonNode.get("id").asText());
        List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionId(socialId);
        Assertions.assertEquals(jsonCategoryType.size(), categoryTypesEntities.size());
        for (JsonNode jsonType : jsonCategoryType) {
            String jsonId = jsonType.asText();
            String saveId = categoryTypesEntities.get(i_type).getId().toString();
            Assertions.assertEquals(jsonId, saveId);
            i_type++;
        }
    }


    @Test
    @DisplayName("Busca ação social por ID com sucesso")
    void findByIdSocialAction() throws Exception {
        // Arrange (Organizar)
        List<SocialActionEntity> saved = socialActionFactory.insertMany(3, categoriesTypesIds, categoryLevelsIds);
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
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertEquals(jsonNode.get("id").asText(), item.getId().toString());
                Assertions.assertEquals(jsonNode.get("name").asText(), item.getName());
                Assertions.assertEquals(jsonNode.get("description").asText(), item.getDescription());
                Assertions.assertFalse(jsonNode.get("createdBy").asText().isEmpty());
                Assertions.assertFalse(jsonNode.get("createdDate").asText().isEmpty());
                Assertions.assertEquals(jsonNode.get("createdBy").asText(), item.getCreatedBy().toString());
                Assertions.assertEquals(jsonNode.get("createdDate").asText(), item.getCreatedDate().format(formatter));
                Assertions.assertTrue(jsonNode.get("deletedDate").isNull());
                Assertions.assertTrue(jsonNode.get("deletedBy").isNull());
                JsonNode jsonLastModifiedBy = jsonNode.get("lastModifiedBy");
                Assertions.assertEquals(jsonLastModifiedBy.isNull() ? null : jsonLastModifiedBy.asText(),
                        item.getLastModifiedBy() == null  ? null : item.getLastModifiedBy().toString()
                );
                JsonNode jsonLastModifiedDate = jsonNode.get("lastModifiedDate");
                Assertions.assertEquals(jsonLastModifiedDate.isNull() ? null : jsonLastModifiedDate.asText(),
                        item.getLastModifiedDate() == null ? null : item.getLastModifiedDate().format(formatter)
                );
                Assertions.assertFalse(jsonNode.get("categoryType").isNull());
                int i_type = 0;
                JsonNode jsonCategoryType = jsonNode.get("categoryType");
                Assertions.assertTrue(jsonCategoryType.isArray());
                List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionId(item.getId());
                Assertions.assertEquals(jsonCategoryType.size(), categoryTypesEntities.size());
                for (JsonNode jsonType : jsonCategoryType) {
                    String jsonId = jsonType.asText();
                    String saveId = categoryTypesEntities.get(i_type).getId().toString();
                    Assertions.assertEquals(jsonId, saveId);
                    i_type++;
                }
    }

    @Test
    @DisplayName("Exclui uma ação social com sucesso")
    void deleteSocialAction() throws Exception {
        // Arrange (Organizar)
        SocialActionEntity item = socialActionFactory.insertOne(socialActionFactory.makeFakeEntity(categoriesTypesIds, categoryLevelsIds));
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
        SocialActionEntity item = socialActionFactory.insertOne(socialActionFactory.makeFakeEntity(categoriesTypesIds, categoryLevelsIds));
        // Modifica alguns dados da ação social
        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("name", item.getName() + "_ATUALIZADO");
        makeBody.put("description", item.getDescription() + "_ATUALIZADO");
        makeBody.put("version", item.getVersion());
        makeBody.put("categoryType", categoriesTypesIds.subList(0, categoriesTypesIds.size() / 2));
//        makeBody.put("categoryLevelIds", forCategoryLevelIds.subList(0, forCategoryLevelIds.size() / 2));
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
        List<CategorySocialActionTypeEntity> categoryTypesEntities = categorySocialActionTypePostgresRepository.findBySocialActionId(
                UUID.fromString(socialActionId)
        );
        List<String> categoryTypeIds = categoryTypesEntities.stream()
                .map(categoryType -> categoryType.getId().toString())
                .collect(Collectors.toList());
        List<CategorySocialActionLevelEntity> categoryLevelsEntities = categorySocialActionLevelPostgresRepository.findBySocialActionEntityId(
                UUID.fromString(socialActionId)
        );
        List<String> categoryLevelIds = categoryLevelsEntities.stream()
                .map(categoryLevel -> categoryLevel.getId().toString())
                .collect(Collectors.toList());
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryType").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryType", hasSize(categoryTypeIds.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryType", containsInAnyOrder(categoryTypeIds.toArray())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryLevelIds").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryLevelIds", hasSize(categoryLevelIds.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryLevelIds", containsInAnyOrder(categoryLevelIds.toArray())))
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

package dev.pantanal.b3.krpv.acao_social.modules.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryGroupFactory;
import dev.pantanal.b3.krpv.acao_social.modules.auth.LoginMock;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository.CategoryGroupRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import static dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupController.ROUTE_CATEGORY_GROUP;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class CategoryGroupControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    LoginMock loginMock;
    @Autowired
    CategoryGroupRepository categoryGroupRepository;
    private String tokenUserLogged;
    @Autowired
    CategoryGroupFactory categoryGroupFactory;

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de grupo de categoria com sucesso")
    void findAllCategoryGroup() throws Exception {
        // Arrange (Organizar)
        List<CategoryGroupEntity> saved = categoryGroupFactory.insertMany(3);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_CATEGORY_GROUP)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (CategoryGroupEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].code").value(item.getCode()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryGroupEntity.id").value(item.getCategoryGroupEntity().getId()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].visibility").value(item.getVisibility()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categories").value(item.getCategories()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").value(item.getCreatedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").value(item.getLastModifiedBy()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").value(item.getCreatedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").value(item.getLastModifiedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").value(item.getDeletedDate()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").value(item.getDeletedBy()));
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova grupo de categoria com sucesso")
    void saveOneCategoryGroup() throws Exception {
        // Arrange (Organizar)
        CategoryGroupEntity item = categoryGroupFactory.makeFakeEntity(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // registrar o módulo JSR-310
        String bodyJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_CATEGORY_GROUP)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJson)
        );
        // Assert (Verificar)
        resultActions
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(item.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }

    @Test
    @DisplayName("Busca grupo de categoria por ID com sucesso")
    void findByIdCategoryGroup() throws Exception {
        // Arrange (Organizar)
        List<CategoryGroupEntity> saved = categoryGroupFactory.insertMany(3);
        CategoryGroupEntity item = saved.get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_CATEGORY_GROUP + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(item.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").value(item.getDeletedBy()));
    }

    @Test
    @DisplayName("(hard-delete) Exclui uma grupo de categoria com sucesso")
    void deleteCategoryGroup() throws Exception {
        // Arrange (Organizar)
        CategoryGroupEntity savedItem = categoryGroupFactory.insertOne(categoryGroupFactory.makeFakeEntity(null, null));
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_CATEGORY_GROUP + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        CategoryGroupEntity deleted = categoryGroupRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }
    // TODO: implementar soft-delete

    @Test
    @DisplayName("Atualiza uma grupo de categoria com sucesso")
    void updateCategoryGroup() throws Exception {
        // Arrange (Organizar)
        CategoryGroupEntity item = categoryGroupFactory.insertOne(categoryGroupFactory.makeFakeEntity(null, null));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // Modifica alguns dados da grupo de categoria
        item.setName(item.getName() + "_ATUALIZADO");
        item.setDescription(item.getDescription() + "_ATUALIZADO");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String updatedCategoryGroupJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_CATEGORY_GROUP + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryGroupJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(item.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.visibility").value(item.getVisibility().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }
}

package dev.pantanal.b3.krpv.acao_social.modules.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryGroupFactory;
import dev.pantanal.b3.krpv.acao_social.modules.auth.LoginMock;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
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
import static dev.pantanal.b3.krpv.acao_social.modulos.category.CategoryController.ROUTE_CATEGORY;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class CategoryControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryPostgresRepository categoryPostgresRepository;

    @Autowired
    ObjectMapper mapper;
    @Autowired
    LoginMock loginMock;
    @Autowired
    CategoryRepository categoryRepository;
    private String tokenUserLogged;
    @Autowired
    CategoryFactory categoryFactory;
    private List<CategoryGroupEntity> groupEntities;
    @Autowired
    CategoryGroupFactory categoryGroupFactory;

    @BeforeEach
    public void setup() throws Exception {
        // TODO: limpar tabela category
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
        this.groupEntities = categoryGroupFactory.insertMany(2);
    }

    @AfterEach
    public void tearDown() {
    }


    @Test
    @DisplayName("lista paginada de categoria com sucesso")
    void findAllCategory() throws Exception {
        // Arrange (Organizar)
        List<CategoryEntity> saved = categoryFactory.insertMany(3, this.groupEntities);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_CATEGORY)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (CategoryEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].code").value(item.getCode()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].categoryGroup.id").value(item.getCategoryGroup().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedBy").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdDate").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastModifiedDate").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedDate").isEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].deletedBy").isEmpty());
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova categoria com sucesso")
    void saveOneCategory() throws Exception {
        // Arrange (Organizar)
        CategoryEntity item = categoryFactory.makeFakeEntity(this.groupEntities.get(0));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // registrar o módulo JSR-310
        String bodyJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_CATEGORY)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyJson)
        );
        // Assert (Verificar)
        resultActions
                // antes verificar se esta vazio
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(item.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryGroup.id").value(item.getCategoryGroup().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }


    @Test
    @DisplayName("Busca categoria por ID com sucesso")
    void findByIdCategory() throws Exception {
        // Arrange (Organizar)
        List<CategoryEntity> saved = categoryFactory.insertMany(3, this.groupEntities);
        CategoryEntity item = saved.get(0);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_CATEGORY + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(item.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryGroup.id").value(item.getCategoryGroup().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }

    @Test
    @DisplayName("(hard-delete) Exclui uma categoria com sucesso")
    void deleteCategory() throws Exception {
        // Arrange (Organizar)
        CategoryEntity savedItem = categoryFactory.insertOne(categoryFactory.makeFakeEntity(this.groupEntities.get(0)));
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_CATEGORY + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        CategoryEntity deleted = categoryRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }
    // TODO: implementar soft-delete

    @Test
    @DisplayName("Atualiza uma categoria com sucesso")
    void updateCategory() throws Exception {
        // Arrange (Organizar)
        CategoryEntity item = categoryFactory.insertOne(categoryFactory.makeFakeEntity(this.groupEntities.get(0)));
        // Modifica alguns dados da categoria
        item.setName(item.getName() + "_ATUALIZADO");
        item.setDescription(item.getDescription() + "_ATUALIZADO");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String updatedCategoryJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_CATEGORY + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(item.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryGroup.id").value(item.getCategoryGroup().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }
}

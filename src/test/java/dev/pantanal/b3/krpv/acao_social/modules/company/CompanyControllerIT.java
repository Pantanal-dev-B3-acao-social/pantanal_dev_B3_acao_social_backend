package dev.pantanal.b3.krpv.acao_social.modules.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CompanyFactory;
import dev.pantanal.b3.krpv.acao_social.modules.auth.LoginMock;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyRepository;
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

import java.util.List;

import static dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyController.ROUTE_COMPANY;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class CompanyControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CompanyPostgresRepository companyPostgresRepository;

    @Autowired
    ObjectMapper mapper;
    @Autowired
    LoginMock loginMock;

    @Autowired
    CompanyRepository companyRepository;
    private String tokenUserLogged;

    @Autowired
    CompanyFactory companyfactory;

    @BeforeEach
    public void setup() throws Exception {
        // TODO: limpar tabela social_action
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de ações sociais com sucesso")
    void findAllCompanies() throws Exception {
        // Arrange (Organizar)
        List<CompanyEntity> saved = companyfactory.insertMany(3);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_COMPANY)
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (CompanyEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].description").value(item.getDescription()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].cnpj").value(item.getCnpj()));
            i++;
        }
    }

    @Test
    @DisplayName("lista paginada,filtrada e ordenada de ações sociais com sucesso")
    void findAllCompaniesFilteredPaged() throws Exception {
        // Arrange (Organizar)
        List<CompanyEntity> saved = companyfactory.insertMany(3);
        long versionTestLong = 1;
        // Prepare filters, sorting, and paging parameters
        String filter = saved.get(0).getName(); //check for Filter
        String sort = "ASC";    //Check for Sorting
        int pageNumber = 0;
        int pageSize = 1;

        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_COMPANY)
                        .param("name", filter)
                        .param("sort", sort)
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize))
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );

        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1))) //should match the page size
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(saved.get(0).getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(saved.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0]description").value(saved.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].cnpj").value(saved.get(0).getCnpj()));
    }

    @Test
    @DisplayName("salva uma nova ação social com sucesso")
    void saveOneCompany() throws Exception {
        // Arrange (Organizar)
        CompanyEntity item = companyfactory.makeFakeEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String socialActionJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_COMPANY)
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(socialActionJson)
        );
        // Assert (Verificar)
        resultActions
                // antes verificar se esta vazio
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value(item.getCnpj()))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("Busca ação social por ID com sucesso")
    void findByIdCompany() throws Exception {
        // Arrange (Organizar)
        List<CompanyEntity> saved = companyfactory.insertMany(3);
        CompanyEntity item = saved.get(0);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_COMPANY + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(item.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value(item.getCnpj()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Exclui uma ação social com sucesso")
    void deleteCompany() throws Exception {
        // Arrange (Organizar)
        CompanyEntity savedItem = companyfactory.insertOne(companyfactory.makeFakeEntity());
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_COMPANY + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        CompanyEntity deleted = companyRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }

    @Test
    @DisplayName("Atualiza uma ação social com sucesso")
    void updateCompany() throws Exception {
        // Arrange (Organizar)
        CompanyEntity savedItem = companyfactory.insertOne(companyfactory.makeFakeEntity());
        // Modifica alguns dados da ação social
        savedItem.setName(savedItem.getName() + "_ATUALIZADO");
        savedItem.setDescription(savedItem.getDescription() + "_ATUALIZADO");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String updatedCompanyJson = objectMapper.writeValueAsString(savedItem);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_COMPANY + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompanyJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedItem.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedItem.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(savedItem.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value(savedItem.getCnpj()))
                .andDo(MockMvcResultHandlers.print());
    }

}

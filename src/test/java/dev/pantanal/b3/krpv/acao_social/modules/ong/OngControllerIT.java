package dev.pantanal.b3.krpv.acao_social.modules.ong;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.OngFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.OngFactory;
import dev.pantanal.b3.krpv.acao_social.modules.auth.LoginMock;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
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

import static dev.pantanal.b3.krpv.acao_social.modulos.ong.OngController.ROUTE_ONG;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class OngControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OngPostgresRepository ongPostgresRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    LoginMock loginMock;

    @Autowired
    OngRepository ongRepository;

    private String tokenUserLogged;

    @Autowired
    OngFactory ongFactory;

    @BeforeEach
    public void setup() throws Exception {
        // TODO: limpar tabela ong
        tokenUserLogged = loginMock.loginUserMock(new LoginUserDto("funcionario1", "123"));
    }

    @AfterEach
    public void tearDown() {
    }


    @Test
    @DisplayName("lista paginada de ong com sucesso")
    void findAllOng() throws Exception {
        // Arrange (Organizar)
        List<OngEntity> saved = ongFactory.insertMany(3);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_ONG)
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (OngEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].cnpj").value(item.getCnpj()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].managerId").value(item.getManagerId()));
            // TODO:
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].created_by").value(item.getCreatedBy()))
//                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].created_date").value(item.getCreatedDate()));
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova ong com sucesso")
    void saveOneOng() throws Exception {
        // Arrange (Organizar)
        OngEntity item = ongFactory.makeFakeEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // registrar o módulo JSR-310
        String bodyJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_ONG)
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson)
        );
        // Assert (Verificar)
        resultActions
                // antes verificar se esta vazio
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value(item.getCnpj()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.managerId").value(item.getManagerId()))
                // TODO:
//                .andExpect(MockMvcResultMatchers.jsonPath("$.created_by").value(item.getCreatedBy()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.created_date").value(item.getCreatedDate()))

                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("Busca ong por ID com sucesso")
    void findByIdOng() throws Exception {
        // Arrange (Organizar)
        List<OngEntity> saved = ongFactory.insertMany(3);
        OngEntity item = saved.get(0);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_ONG + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value(item.getCnpj()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.managerId").value(item.getManagerId()))

                // TODO:
//                .andExpect(MockMvcResultMatchers.jsonPath("$.create_by").value(item.getCreatedBy()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cateated_date").value(item.getCreatedDate()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("(hard-delete) Exclui uma ong com sucesso")
    void deleteOng() throws Exception {
        // Arrange (Organizar)
        OngEntity savedItem = ongFactory.insertOne(ongFactory.makeFakeEntity());
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_ONG + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        OngEntity deleted = ongRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }
    // TODO: implementar soft-delete

    @Test
    @DisplayName("Atualiza uma ong com sucesso")
    void updateOng() throws Exception {
        // Arrange (Organizar)
        OngEntity savedItem = ongFactory.insertOne(ongFactory.makeFakeEntity());
        // Modifica alguns dados da ong
        savedItem.setName(savedItem.getName() + "_ATUALIZADO");
        savedItem.setCnpj(savedItem.getCnpj() + "_ATUALIZADO");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String updatedOngJson = objectMapper.writeValueAsString(savedItem);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_ONG + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedOngJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedItem.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(savedItem.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value(savedItem.getCnpj()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.managerId").value(savedItem.getManagerId()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.create_by").value(savedItem.getCreatedBy()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.cateated_date").value(savedItem.getCreatedDate()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last_modified_by").value(savedItem.getLastModifiedBy()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last_modified_date").value(savedItem.getLastModifiedDate()))
                .andDo(MockMvcResultHandlers.print());
    }
}

package dev.pantanal.b3.krpv.acao_social.modules.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.KeycloakUser;
import dev.pantanal.b3.krpv.acao_social.modulos.user.UserFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.user.UserService;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
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
import java.util.Random;
import java.util.UUID;

import static dev.pantanal.b3.krpv.acao_social.modulos.user.UserController.ROUTE_USER;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class UserControllerIT {

    UserFactory userFactory;
    @Autowired
    MockMvc mockMvc;
    private static final Random random = new Random();
    @Autowired
    ObjectMapper mapper;
    private String tokenUserLogged;
    @Autowired
    GenerateTokenUserForLogged generateTokenUserForLogged;
    @Autowired
    LoginMock loginMock;
    private DateTimeFormatter formatter;
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;

    @BeforeEach
    public void setup() throws Exception {
        // token de login
        this.tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        this.loginMock.authenticateWithToken(tokenUserLogged);
        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        userFactory = new UserFactory(this.tokenUserLogged, objectMapper, userService);
    }

    @AfterEach
    public void tearDown() {}


    @Test
    @DisplayName("lista paginada de User com sucesso")
    void findAllUser() throws Exception {
        // Arrange (Organizar)
        List<KeycloakUser> saved = userFactory.createMany(3);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_USER)
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (KeycloakUser item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].username").value(item.getUsername()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].enabled").value(item.getEnabled()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].firstName").value(item.getFirstName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].lastName").value(item.getLastName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].email").value(item.getEmail()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].emailVerified").value(item.getEmailVerified()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].attributes").value(item.getAttributes()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdTimestamp").value(item.getCreatedTimestamp()));
            i++;
        }
    }

    @Test
    @DisplayName("salva uma nova User com sucesso")
    void saveOneUser() throws Exception {
        // Arrange (Organizar)
        KeycloakUser item = userFactory.createOne();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String socialActionJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_USER)
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(socialActionJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(item.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enabled").value(item.getEnabled()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailVerified").value(item.getEmailVerified()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(item.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(item.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributes").value(item.getAttributes()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(item.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTimestamp").value(item.getCreatedTimestamp()));
    }


    @Test
    @DisplayName("Busca User por ID com sucesso")
    void findByIdUser() throws Exception {
        // Arrange (Organizar)
        List<KeycloakUser> saved = userFactory.createMany(3);
        KeycloakUser item = saved.get(0);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_USER + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(item.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enabled").value(item.getEnabled()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailVerified").value(item.getEmailVerified()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(item.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(item.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributes").value(item.getAttributes()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(item.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTimestamp").value(item.getCreatedTimestamp()));

    }

    @Test
    @DisplayName("Exclui uma User com sucesso")
    void deleteUser() throws Exception {
        // Arrange (Organizar)
        KeycloakUser savedItem = userFactory.createOne();
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_USER + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        KeycloakUser deleted = userService.findById(UUID.fromString(savedItem.getId()), tokenUserLogged.toString());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }

    @Test
    @DisplayName("Atualiza uma User com sucesso")
    void updateUser() throws Exception {
        // Arrange (Organizar)
        KeycloakUser savedItem = userFactory.createOne();
        // Modifica alguns dados da User
        savedItem.setUsername(savedItem.getFirstName() + "_ATUALIZADO");
        savedItem.setLastName(savedItem.getLastName() + "_ATUALIZADO");
        String updatedUserJson = objectMapper.writeValueAsString(savedItem);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_USER + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(savedItem.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enabled").value(savedItem.getEnabled()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.emailVerified").value(savedItem.getEmailVerified()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(savedItem.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(savedItem.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.attributes").value(savedItem.getAttributes()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(savedItem.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTimestamp").value(savedItem.getCreatedTimestamp()));
    }

}

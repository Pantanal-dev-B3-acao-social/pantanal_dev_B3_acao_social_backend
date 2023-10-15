package dev.pantanal.b3.krpv.acao_social.modules.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.KeycloakUser;
import dev.pantanal.b3.krpv.acao_social.modulos.user.UserFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.user.UserService;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserCreateDto;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

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
    List<KeycloakUser> saveds;

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
    public void tearDown() {
        if (this.saveds != null) {
            for(KeycloakUser user : this.saveds) {
                this.userService.delete(UUID.fromString(user.getId()), tokenUserLogged);
            }
        }
        this.saveds = null;
    }

    public static JsonNode inArray(JsonNode contentArray, String idToFind) {
        for (JsonNode userNode : contentArray) {
            if (userNode.get("id").asText().equals(idToFind)) {
                return userNode;
            }
        }
        return null;
    }

    @Test
    @DisplayName("lista paginada de User com sucesso")
    void findAllUser() throws Exception {
        // Arrange (Organizar)
        this.saveds = userFactory.createMany(3);
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
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)))
        ;
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String jsonResponse = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode contentArray = jsonNode.get("content");
        if (contentArray.isArray()) {
            for (KeycloakUser user : this.saveds) {
                JsonNode element = inArray(contentArray, user.getId().toString());
                Assertions.assertEquals(element.get("username").asText(), user.getUsername().toString());
                Assertions.assertEquals(element.get("enabled").asBoolean(), user.getEnabled());
                Assertions.assertEquals(element.get("firstName").asText(), user.getFirstName());
                Assertions.assertEquals(element.get("lastName").asText(), user.getLastName());
//                Assertions.assertEquals(element.get("emailVerified", user.getEmailVerified().toString());
                Assertions.assertEquals(element.get("email").asText(), user.getEmail());
            }
        }
    }

    @Test
    @DisplayName("salva uma nova User com sucesso")
    void saveOneUser() throws Exception {
        // Arrange (Organizar)
        UserCreateDto userDto = userFactory.makeUserDto();
        String jsonResquest = objectMapper.writeValueAsString(userDto);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_USER)
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonResquest)
        );
        MvcResult mvcResult = resultActions.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String jsonResponse = response.getContentAsString();
        String userId = jsonResponse;
        KeycloakUser item = userService.findById(UUID.fromString(userId), tokenUserLogged.toString());
        // Assert (Verificar)
        Assertions.assertEquals(userDto.username(), item.getUsername());
        Assertions.assertEquals(userDto.enabled(), item.getEnabled());
        Assertions.assertEquals(userDto.firstName(), item.getFirstName());
        Assertions.assertEquals(userDto.lastName(), item.getLastName());
//        Assertions.assertEquals(userDto.attributes(), item.getAttributes());
        Assertions.assertEquals(userDto.email(), item.getEmail());
        Assertions.assertEquals(false, item.getEmailVerified());
    }


    @Test
    @DisplayName("Busca User por ID com sucesso")
    void findByIdUser() throws Exception {
        // Arrange (Organizar)
        this.saveds = userFactory.createMany(3);
        KeycloakUser item = this.saveds.get(0);
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
        this.saveds = userFactory.createMany(1);
        KeycloakUser savedItem = this.saveds.get(0);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_USER + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.findById(UUID.fromString(savedItem.getId()), tokenUserLogged.toString());
        });
        this.saveds = null;
    }

    @Test
    @DisplayName("Atualiza uma User com sucesso")
    void updateUser() throws Exception {
        // Arrange (Organizar)
        this.saveds = userFactory.createMany(1);
        KeycloakUser savedItem = this.saveds.get(0);
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

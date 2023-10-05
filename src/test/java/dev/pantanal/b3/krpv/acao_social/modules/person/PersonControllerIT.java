package dev.pantanal.b3.krpv.acao_social.modules.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.PersonFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonPostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtil;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.person.PersonController.ROUTE_PERSON;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class PersonControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PersonPostgresRepository personPostgresRepository;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    PersonRepository personRepository;
    private String tokenUserLogged;
    @Autowired
    PersonFactory personFactory;
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
    UUID userIdLoggedFuncionarioCompany;
    UUID userIdLoggedGerenteCompany;
    List<UUID> usersIds = new ArrayList<>();

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        loginMock.authenticateWithToken(tokenUserLogged);
        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        // user keyclock
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userIdLoggedFuncionarioCompany = UUID.fromString(authentication.getName());
        int amount = 4;
        for(int i = 0; i < amount; i++) {
            usersIds.add(UUID.randomUUID());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("lista paginada de person com sucesso")
    void findAllSession() throws Exception {
        // Arrange (Organizar)
        List<PersonEntity> saved = personFactory.insertMany(usersIds.size(), usersIds);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_PERSON)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(4)));
        int i = 0;
        for (PersonEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].name").value(item.getName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].dateBirth").value(item.getDateBirth().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].status").value(item.getStatus().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].userId").value(item.getUserId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].cpf").value(item.getCpf()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].createdBy").isNotEmpty())
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
    @DisplayName("salva uma nova person com sucesso")
    void saveOneSession() throws Exception {
        // Arrange (Organizar)
        PersonEntity item = personFactory.makeFakeEntity(UUID.randomUUID());
        String jsonRequest = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_PERSON)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateBirth").value(item.getDateBirth().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(item.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty());
    }


    @Test
    @DisplayName("Busca person por ID com sucesso")
    void findByIdSession() throws Exception {
        // Arrange (Organizar)
        List<PersonEntity> saved = personFactory.insertMany(usersIds.size(), usersIds);
        PersonEntity item = saved.get(0);
        // TODO:        item.setCreatedBy(userLoggedId);
        String createdByString = Optional.ofNullable(item.getCreatedBy()).map(UUID::toString).orElse(null);
        String lastModifiedByString = Optional.ofNullable(item.getLastModifiedBy()).map(UUID::toString).orElse(null);
        String deletedByString = Optional.ofNullable(item.getDeletedBy()).map(UUID::toString).orElse(null);
        String deletedDateString = Optional.ofNullable(item.getDeletedDate()).map(LocalDateTime::toString).orElse(null);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_PERSON + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateBirth").value(item.getDateBirth().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(item.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(createdByString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").value(deletedDateString))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").value(deletedByString))
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
    @DisplayName("(hard-delete) Exclui uma person com sucesso")
    void deleteSession() throws Exception {
        // Arrange (Organizar)
        PersonEntity savedItem = personFactory.insertOne(personFactory.makeFakeEntity(UUID.randomUUID()));
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_PERSON + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
        PersonEntity deleted = personRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted); // Deve ser nulo para passar o teste

    }

    @Test
    @DisplayName("Atualiza uma person com sucesso")
    void updateSession() throws Exception {
        // Arrange (Organizar)
        PersonEntity item = personFactory.insertOne(personFactory.makeFakeEntity(UUID.randomUUID()));
        // Modifica alguns dados da person
        LocalDateTime dateBirthUpdate = item.getDateBirth().plusHours(2).plusMinutes(40);
        StatusEnum statusEnum = new EnumUtil<StatusEnum>().getRandomValueDiff(item.getStatus());
        item.setName(item.getName() + "_ATUALIZADO");
        item.setDateBirth(dateBirthUpdate);
        item.setStatus(statusEnum);
        // TODO: quais dados falta modificar para testar?
        String updatedJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_PERSON + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateBirth").value(item.getDateBirth().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(item.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(item.getUserId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value(item.getCreatedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").value(item.getCreatedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value(item.getLastModifiedBy().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").value(item.getLastModifiedDate().format(formatter)));
    }
}

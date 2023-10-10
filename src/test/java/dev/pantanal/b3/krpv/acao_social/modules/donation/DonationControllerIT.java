package dev.pantanal.b3.krpv.acao_social.modules.donation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.*;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.repository.DonationRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCnpj;
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
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationController.ROUTE_DONATION;
import static org.hamcrest.Matchers.hasSize;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class DonationControllerIT {

    @Autowired
    GenerateTokenUserForLogged generateTokenUserForLogged;
    @Autowired
    LoginMock loginMock;
    private String tokenUserLogged;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    private DateTimeFormatter formatter;
    UUID userIdLoggedFuncionarioCompany;
    @Autowired
    GeneratorCnpj generatorCnpj;
    @Autowired
    DonationFactory donationFactory;
    @Autowired
    private SocialActionFactory socialActionFactory;
    @Autowired
    private CategoryFactory categoryFactory;
    List<CategoryEntity> categoriesType;
    List<CategoryEntity> categoriesLevel;
    @Autowired
    PersonFactory personFactory;
    List<UUID> usersRandom = IntStream.range(0, 4)
            .mapToObj(i -> UUID.randomUUID())
            .collect(Collectors.toList());
    List<PersonEntity> personEntities;
    List<SocialActionEntity> socialActionEntities;
    @Autowired
    DonationRepository donationRepository;

    @BeforeEach
    public void setup() throws Exception {
        tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto("funcionario1", "123"));
        loginMock.authenticateWithToken(tokenUserLogged);
        // formatar data hora
        this.formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        // mapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        //
        categoriesType = categoryFactory.makeFakeByGroup(2, "social action type", "grupo de categorias para usar no TIPO de ação social");;
        categoriesLevel = categoryFactory.makeFakeByGroup(2, "social action level", "grupo de categorias para usar no NÍVEL de ação social");
        personEntities = personFactory.insertMany(usersRandom.size(), usersRandom);
        socialActionEntities = socialActionFactory.insertManyFull(3, categoriesType, categoriesLevel);
    }

    @AfterEach
    public void tearDown() {}

    @Test
    @DisplayName("lista paginada de donation com sucesso")
    void findAllDonation() throws Exception {
        // Arrange (Organizar)
        List<DonationEntity> saved = donationFactory.insertMany(3, socialActionEntities, personEntities, personEntities);
        // Act (ação)
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_DONATION)
                .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        perform
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(3)));
        int i = 0;
        for (DonationEntity item : saved) {
            perform
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].id").value(item.getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].socialActionEntity.id").value(item.getSocialActionEntity().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].donatedByEntity.id").value(item.getDonatedByEntity().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].donationDate").value(item.getDonationDate().format(formatter)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].valueMoney").value(item.getValueMoney().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].motivation").value(item.getMotivation()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedBy.id").value(item.getApprovedBy().getId().toString()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content[" + i + "].approvedDate").value(item.getApprovedDate().format(formatter)))
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
    @DisplayName("salva uma nova donation com sucesso")
    void saveOneDonation() throws Exception {
        // Arrange (Organizar)
        PersonEntity approved = personEntities.get(0);
        PersonEntity donor = personEntities.get(1);
        SocialActionEntity socialActionEntity = socialActionEntities.get(0);
        DonationEntity item = donationFactory.makeFakeEntity(socialActionEntity, donor, approved);
        String jsonRequest = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(ROUTE_DONATION)
                    .header("Authorization", "Bearer " + tokenUserLogged)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialActionEntity.id").value(item.getSocialActionEntity().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.donatedByEntity.id").value(item.getDonatedByEntity().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.donationDate").value(item.getDonationDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueMoney").value(item.getValueMoney().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivation").value(item.getMotivation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedBy.id").value(item.getApprovedBy().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvedDate").value(item.getApprovedDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedBy").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deletedDate").isEmpty());
    }

    @Test
    @DisplayName("Busca donation por ID com sucesso")
    void findByIdDonation() throws Exception {
        // Arrange (Organizar)
        List<DonationEntity> saved = donationFactory.insertMany(3, socialActionEntities, personEntities, personEntities);
        DonationEntity item = saved.get(0);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(ROUTE_DONATION + "/{id}", item.getId().toString())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialActionEntity.id").value(item.getSocialActionEntity().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.donatedByEntity.id").value(item.getDonatedByEntity().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.donationDate").value(item.getDonationDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueMoney").value(item.getValueMoney().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivation").value(item.getMotivation()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate").isNotEmpty())
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
    @DisplayName("(hard-delete) Exclui uma donation com sucesso")
    void deleteDonation() throws Exception {
        // Arrange (Organizar)
        PersonEntity approved = personEntities.get(0);
        PersonEntity donor = personEntities.get(1);
        SocialActionEntity socialActionEntity = socialActionEntities.get(0);
        DonationEntity donationEntity = donationFactory.makeFakeEntity(socialActionEntity, donor, approved);
        DonationEntity savedItem = donationFactory.insertOne(donationEntity);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete(ROUTE_DONATION + "/{id}", savedItem.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        DonationEntity deleted = donationRepository.findById(savedItem.getId());
        Assertions.assertNull(deleted);
    }

    @Test
    @DisplayName("Atualiza uma donation com sucesso")
    void updateDonation() throws Exception {
        // Arrange (Organizar)
        PersonEntity approved = personEntities.get(0);
        PersonEntity donor = personEntities.get(1);
        SocialActionEntity socialActionEntity = socialActionEntities.get(0);
        DonationEntity donationEntity = donationFactory.makeFakeEntity(socialActionEntity, donor, approved);
        DonationEntity item = donationFactory.insertOne(donationEntity);
//        BigDecimal updateValue = new BigDecimal();
        // Modifica alguns dados da donation
        item.setApprovedDate(item.getApprovedDate().plusHours(-2));
        item.setDonatedByEntity(personEntities.get(2));
        item.setSocialActionEntity(socialActionEntities.get(1));
        item.setDonationDate(item.getDonationDate().plusHours(-3));
        item.setValueMoney(item.getValueMoney().add(item.getValueMoney().add(new BigDecimal("5.74"))));
        item.setMotivation(item.getMotivation() + "_ATUALIZADO");

        item.setApprovedBy(personEntities.get(3));
        item.setApprovedDate(item.getApprovedDate().plusHours(-1));
        // TODO: quais dados falta modificar para testar?
        String updatedJson = objectMapper.writeValueAsString(item);
        // Act (ação)
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(ROUTE_DONATION + "/{id}", item.getId())
                        .header("Authorization", "Bearer " + tokenUserLogged)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson)
        );
        // Assert (Verificar)
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.socialActionEntity.id").value(item.getSocialActionEntity().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.donatedByEntity.id").value(item.getDonatedByEntity().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.donationDate").value(item.getDonationDate().format(formatter)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valueMoney").value(item.getValueMoney().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivation").value(item.getMotivation()))
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
                );;
    }
}
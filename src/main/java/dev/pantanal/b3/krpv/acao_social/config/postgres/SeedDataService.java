package dev.pantanal.b3.krpv.acao_social.config.postgres;

import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.*;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SeedDataService {

    private final SocialActionFactory socialActionFactory;
    private final SessionFactory sessionFactory;
    private final CategoryFactory categoryFactory;
    private final CategoryGroupFactory categoryGroupFactory;
    private final OngFactory ongFactory;
    private final InvestmentFactory investmentFactory;
    private final DonationFactory donationFactory;
    private final PersonFactory personFactory;
    private final CompanyFactory companyFactory;
    private final PresenceFactory presenceFactory;
    private final VoluntaryFactory voluntaryFactory;
    private final InterestFactory interestFactory;
    private final PcdFactory pcdFactory;
    private final PcdPersonFactory pcdPersonFactory;


    @Value("${acao-social.keyclock.adminUsername}")
    private String adminUsername;

    @Value("${acao-social.keyclock.adminPassword}")
    private String adminPassword;

    @Autowired
    GenerateTokenUserForLogged generateTokenUserForLogged;
    @Autowired
    LoginMock loginMock;

    private static final Random random = new Random();

    @Autowired
    public SeedDataService(
            JdbcTemplate jdbcTemplate,
            SocialActionFactory socialActionFactory,
            SessionFactory sessionFactory,
            CategoryFactory categoryFactory,
            CategoryGroupFactory categoryGroupFactory,
            OngFactory ongFactory,
            InvestmentFactory investmentFactory,
            DonationFactory donationFactory,
            PersonFactory personFactory,
            CompanyFactory companyFactory,
            PresenceFactory presenceFactory,
            VoluntaryFactory voluntaryFactory,
            InterestFactory interestFactory,
            PcdFactory pcdFactory,
            PcdPersonFactory pcdPersonFactory
    ) {
        this.socialActionFactory = socialActionFactory;
        this.sessionFactory = sessionFactory;
        this.categoryFactory = categoryFactory;
        this.categoryGroupFactory = categoryGroupFactory;
        this.ongFactory = ongFactory;
        this.investmentFactory = investmentFactory;
        this.donationFactory = donationFactory;
        this.personFactory = personFactory;
        this.companyFactory = companyFactory;
        this.presenceFactory = presenceFactory;
        this.voluntaryFactory = voluntaryFactory;
        this.interestFactory = interestFactory;
        this.pcdFactory = pcdFactory;
        this.pcdPersonFactory = pcdPersonFactory;

    }

    public void executeAllSeed() {
        String tokenUserLogged = generateTokenUserForLogged.loginUserMock(new LoginUserDto(adminUsername, adminPassword));
        loginMock.authenticateWithToken(tokenUserLogged);
        // Arrange (Organizar) category
        List<CategoryGroupEntity> groupEntitiesParents = this.categoryGroupFactory.insertMany(3, null);
        List<CategoryGroupEntity> groupEntitiesSons = this.categoryGroupFactory.insertMany(3, groupEntitiesParents.get(0));
        // Arrange (Organizar) social action
        List<CategoryGroupEntity> categoryGroupLevelEntities = new ArrayList<>();
        CategoryGroupEntity levelGroupEntity = categoryGroupFactory.makeFakeEntity("Social Action Level", "level of social action", null);
        CategoryGroupEntity levelGroupSaved = categoryGroupFactory.insertOne(levelGroupEntity);
        categoryGroupLevelEntities.add(levelGroupSaved);
        List<CategoryEntity> categoryLevels = this.categoryFactory.insertMany(3, categoryGroupLevelEntities);
        List<UUID> categoryLevelsIds = categoryLevels.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());

        List<CategoryGroupEntity> categoryGroupTypesEntities = new ArrayList<>();
        CategoryGroupEntity typeGroupEntity = categoryGroupFactory.makeFakeEntity("social action type", "grupo de categorias para usar no TIPO de ação social", null);
        CategoryGroupEntity typeGroupSaved = categoryGroupFactory.insertOne(typeGroupEntity);
        categoryGroupTypesEntities.add(typeGroupSaved); //As categorias Desse vetor sempre Estarão relacionadas a um grupo especifico de categorias possiveis para uma entidade
        List<CategoryEntity> categoriesTypes = this.categoryFactory.insertMany(6, categoryGroupTypesEntities); // as 6 categorias pertencem a este grupo

        List<UUID> categoriesTypesIds = categoriesTypes.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
        List<UUID> usersRandom = IntStream.range(0, 30)
                .mapToObj(i -> UUID.randomUUID())
                .collect(Collectors.toList());
        // SEED
        this.categoryFactory.insertMany(20, groupEntitiesParents);
        this.categoryFactory.insertMany(20, groupEntitiesSons);
        List<PersonEntity> personEntities = this.personFactory.insertMany(usersRandom.size(), usersRandom);
        this.companyFactory.insertMany(4);
        this.ongFactory.insertMany(10);
        List<SocialActionEntity> socialActionEntities = this.socialActionFactory.insertMany(20, categoriesTypesIds, categoryLevelsIds);
        this.investmentFactory.insertMany(40);
        List<SessionEntity> sessions = this.sessionFactory.insertMany(40);
        this.donationFactory.insertMany(40, socialActionEntities, personEntities, personEntities);
        this.presenceFactory.insertMany(40, personEntities, sessions, personEntities);
        this.voluntaryFactory.insertMany(40);
        this.interestFactory.insertMany(20, personEntities, categoriesTypes);
        this.pcdFactory.insertMany(20);
        this.pcdPersonFactory.insertMany(20);
    }

}
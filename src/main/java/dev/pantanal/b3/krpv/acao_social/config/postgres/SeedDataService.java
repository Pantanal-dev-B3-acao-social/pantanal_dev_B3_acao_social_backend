package dev.pantanal.b3.krpv.acao_social.config.postgres;

import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.*;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            PersonFactory personFactory
    ) {
        this.socialActionFactory = socialActionFactory;
        this.sessionFactory = sessionFactory;
        this.categoryFactory = categoryFactory;
        this.categoryGroupFactory = categoryGroupFactory;
        this.ongFactory = ongFactory;
        this.investmentFactory = investmentFactory;
        this.donationFactory = donationFactory;
        this.personFactory = personFactory;
    }

    public void executeAllSeed() {
        // Arrange (Organizar) category
        List<CategoryGroupEntity> groupEntities = this.categoryGroupFactory.insertMany(4, null);
        // Arrange (Organizar) social action
        List<CategoryGroupEntity> typesGroupEntities = new ArrayList<>();
        CategoryGroupEntity typeGroupEntity = categoryGroupFactory.makeFakeEntity("social action type", "grupo de categorias para usar no TIPO de ação social", null);
        CategoryGroupEntity typeGroupSaved = categoryGroupFactory.insertOne(typeGroupEntity);
        typesGroupEntities.add(typeGroupSaved);
        List<CategoryEntity> categoriesType = categoryFactory.insertMany(6, typesGroupEntities); // as 6 categorias pertencem a este grupo
        List<UUID> forCategoryTypeIds = categoriesType.stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
        List<UUID> usersRandom = IntStream.range(0, 3000)
                .mapToObj(i -> UUID.randomUUID())
                .collect(Collectors.toList());
        // SEED
        this.categoryFactory.insertMany(100, groupEntities);
        List<PersonEntity> personEntities = this.personFactory.insertMany(usersRandom.size(), usersRandom);
//        this.companyFactory.insertMany(4);
        this.ongFactory.insertMany(10);
        List<SocialActionEntity> socialActionEntities = this.socialActionFactory.insertMany(20, forCategoryTypeIds, null);
        this.sessionFactory.insertMany(100);
        this.investmentFactory.insertMany(250);
        this.donationFactory.insertMany(450, socialActionEntities, personEntities, personEntities);
    }

}
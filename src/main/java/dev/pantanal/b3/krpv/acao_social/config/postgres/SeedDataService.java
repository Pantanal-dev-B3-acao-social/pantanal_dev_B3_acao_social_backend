package dev.pantanal.b3.krpv.acao_social.config.postgres;

import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryGroupFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SessionFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeedDataService {

    private final SocialActionFactory socialActionFactory;
    private final SessionFactory sessionFactory;
    private final CategoryFactory categoryFactory;
    private final CategoryGroupFactory categoryGroupFactory;

    @Autowired
    public SeedDataService(
            JdbcTemplate jdbcTemplate,
            SocialActionFactory socialActionFactory,
            SessionFactory sessionFactory,
            CategoryFactory categoryFactory,
            CategoryGroupFactory categoryGroupFactory
    ) {
        this.socialActionFactory = socialActionFactory;
        this.sessionFactory = sessionFactory;
        this.categoryFactory = categoryFactory;
        this.categoryGroupFactory = categoryGroupFactory;
    }

    public void executeAllSeed() {
        List<CategoryGroupEntity> groupEntities = this.categoryGroupFactory.insertMany(4);
        this.categoryFactory.insertMany(100, groupEntities);
//        this.companyFactory.insertMany(4);
//        this.ongFactory.insertMany(10);
        this.socialActionFactory.insertMany(20);
        this.sessionFactory.insertMany(100);
    }

}
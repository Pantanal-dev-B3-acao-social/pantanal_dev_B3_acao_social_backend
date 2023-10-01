package dev.pantanal.b3.krpv.acao_social.config.postgres;

import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SessionFactory;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
//@DependsOn("postgresDatabaseInitialization")
//@Order(5)
public class SeedDataService {

    private final SocialActionFactory socialActionFactory;
    private final SessionFactory sessionFactory;
    private final CategoryFactory categoryFactory;

    @Autowired
    public SeedDataService(
            JdbcTemplate jdbcTemplate,
            SocialActionFactory socialActionFactory,
            SessionFactory sessionFactory,
            CategoryFactory categoryFactory
    ) {
        this.socialActionFactory = socialActionFactory;
        this.sessionFactory = sessionFactory;
        this.categoryFactory = categoryFactory;
    }

    public void executeAllSeed() {
        this.categoryFactory.insertMany(100);
//        this.companyFactory.insertMany(4);
//        this.ongFactory.insertMany(10);
        this.socialActionFactory.insertMany(20);
        this.sessionFactory.insertMany(100);
    }

}
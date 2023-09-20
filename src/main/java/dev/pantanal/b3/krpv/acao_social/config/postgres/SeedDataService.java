package dev.pantanal.b3.krpv.acao_social.config.postgres;

import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SeedDataService {

    private final SocialActionFactory socialActionFactory;

    @Autowired
    public SeedDataService(JdbcTemplate jdbcTemplate, SocialActionFactory socialActionFactory) {
        this.socialActionFactory = socialActionFactory;
    }

    public void executeAllSeed() {
//        this.companyFactory.insertMany(4);
//        this.ongFactory.insertMany(10);
        this.socialActionFactory.insertMany(99);
    }

}
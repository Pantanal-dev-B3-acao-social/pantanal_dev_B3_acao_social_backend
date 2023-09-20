package dev.pantanal.b3.krpv.acao_social.config.postgres;

import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.SocialActionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class SeedDataService {

    private final JdbcTemplate jdbcTemplate;
    private final SocialActionFactory socialActionFactory;

    @Autowired
    public SeedDataService(JdbcTemplate jdbcTemplate, SocialActionFactory socialActionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.socialActionFactory = socialActionFactory;
    }

    public void executeAllSeed() {
        this.socialActionFactory.insert(this.socialActionFactory.makeFake(), this.jdbcTemplate);
//        this.companyFactory.insert(this.companyFactory.makeFake());
//        this.ongFactory.insert(this.ongFactory.makeFake());
    }

}
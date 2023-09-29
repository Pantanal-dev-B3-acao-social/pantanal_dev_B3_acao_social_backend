package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.SocialActionDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class SocialActionFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private SocialActionRepository socialActionRepository;

    @Autowired
    public SocialActionFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SocialActionDto makeFake() {
        return new SocialActionDto(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.lorem().sentence(),
//                faker.name().title(),
            faker.number().randomNumber()

                // string ongId = findOneRandom("ong");
                // string levelId = findOneRandom("category_project_level");
                // string typeId = findOneRandom("category_project_type");
        );
    }

    public SocialActionEntity makeFakeEntity() {
        UUID createdBy = UUID.randomUUID();
        UUID lastModifiedBy = null;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        return new SocialActionEntity(
                1L,
                null,
                faker.name().fullName(),
                faker.lorem().sentence(),
                createdBy,
                lastModifiedBy,
                createdDate,
                lastModifiedDate,
                null,
                null
//                ,SessionEntity
                // string ongId = findOneRandom("ong");
                // string levelId = findOneRandom("category_project_level");
                // string typeId = findOneRandom("category_project_type");
        );
    }

    public String findOneRandom(String table) {
        String sql = "SELECT * FROM "+ table +" ORDER BY RANDOM() LIMIT 1";
        try {
            String id = jdbcTemplate.queryForObject(sql, String.class);
            return id;
        } catch (EmptyResultDataAccessException e) {
            // Trate o caso em que a tabela está vazia
            // TODO: lançar exceção
            return null;
        }
    }

    public SocialActionEntity insertOne(SocialActionEntity toSave) {
        SocialActionEntity saved = socialActionRepository.save(toSave);
        return saved;
    }

    public List<SocialActionEntity> insertMany(int amount) {
        List<SocialActionEntity> socials = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            SocialActionEntity socialActionEntity = this.makeFakeEntity();
            socials.add(this.insertOne(socialActionEntity));
        }
        return socials;
    }
}

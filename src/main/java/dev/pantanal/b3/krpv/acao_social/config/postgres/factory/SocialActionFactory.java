package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;
import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.SocialActionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Random;
import java.util.UUID;

@Component
public class SocialActionFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    @Autowired
    public SocialActionFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final JdbcTemplate jdbcTemplate;
    public SocialActionDto makeFake() {
        return new SocialActionDto(
            UUID.randomUUID(),
            faker.name().fullName(),
            faker.lorem().sentence()
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

    public void insertOne(SocialActionDto socialActionDto) {
        String sql = "INSERT INTO social_action (id, name, description, version, organizer) VALUES (?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(
            sql,
            socialActionDto.id(),
            socialActionDto.name(),
            socialActionDto.description(),
            1,
            socialActionDto.description()
        );
    }

    public void insertMany(int amount) {
        for (int i=0; i<amount; i++) {
            this.insertOne(this.makeFake());
        }
    }
}


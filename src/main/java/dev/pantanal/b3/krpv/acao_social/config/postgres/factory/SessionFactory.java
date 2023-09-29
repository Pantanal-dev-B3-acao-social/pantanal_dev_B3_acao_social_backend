package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtil;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCode;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;

@Component
public class SessionFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private GeneratorCode generatorCode;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public SessionFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SessionEntity makeFakeEntity() {
        UUID createdBy = UUID.randomUUID();
        UUID deleteBy = null;
        UUID lastModifiedBy = null;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        LocalDateTime time = lastModifiedDate.plusHours(2).plusMinutes(40);
        FindRegisterRandom findRegisterRandom = new FindRegisterRandom<SocialActionEntity>(entityManager);
        List<SocialActionEntity> socialActions = findRegisterRandom.execute("social_action", 1, SocialActionEntity.class);
        StatusEnum statusEnum = new EnumUtil<StatusEnum>().getRandomValue(StatusEnum.class);
        VisibilityEnum visibilityEnum = new EnumUtil<VisibilityEnum>().getRandomValue(VisibilityEnum.class);
        SessionEntity sessionEntity = new SessionEntity(
                1L,
                UUID.randomUUID(),
                faker.lorem().sentence(),
                time,
                statusEnum,
                visibilityEnum,
                createdBy,
                lastModifiedBy,
                createdDate,
                lastModifiedDate,
                null,
                deleteBy,
                socialActions.get(0) // TODO: se get(0) estiver null manda null para nao dar erro
//                localId,
//                presencesId[],
//                resourcesId[]
        );
        return sessionEntity;
    }

    public SessionEntity insertOne(SessionEntity toSave) {
        SessionEntity saved = sessionRepository.save(toSave);
        return saved;
    }

    public List<SessionEntity> insertMany(int amount) {
        List<SessionEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            SessionEntity entity = this.makeFakeEntity();
            entities.add(this.insertOne(entity));
        }
        return entities;
    }
    
}
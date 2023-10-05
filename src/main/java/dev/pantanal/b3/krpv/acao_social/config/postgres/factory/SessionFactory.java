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
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        LocalDateTime dateStart = lastModifiedDate.plusHours(2).plusMinutes(40);
        LocalDateTime dateEnd = dateStart.plusHours(2).plusMinutes(0);
        FindRegisterRandom<SocialActionEntity> findRegisterRandom = new FindRegisterRandom<SocialActionEntity>(entityManager);
        List<SocialActionEntity> socialActions = findRegisterRandom.execute("social_action", 1, SocialActionEntity.class);
        StatusEnum statusEnum = new EnumUtil<StatusEnum>().getRandomValue(StatusEnum.class);
        VisibilityEnum visibilityEnum = new EnumUtil<VisibilityEnum>().getRandomValue(VisibilityEnum.class);
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setVersion(1L);
        sessionEntity.setDescription(faker.lorem().sentence());
        sessionEntity.setDateStartTime(dateStart);
        sessionEntity.setDateEndTime(dateEnd);
        sessionEntity.setStatus(statusEnum);
        sessionEntity.setVisibility(visibilityEnum);
        sessionEntity.setSocialAction(socialActions.get(0));
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

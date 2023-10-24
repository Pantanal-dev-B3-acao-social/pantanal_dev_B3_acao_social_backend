package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.repository.PresenceRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.GeneretorCpf;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PresenceFactory {

    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    GeneretorCpf generetorCpf;

    @Autowired
    public PresenceFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PresenceEntity makeFakeEntity(
            PersonEntity person,
            SessionEntity session,
            PersonEntity approved
    ) {
        Integer index = random.nextInt(0, 10);
        LocalDateTime approvedDate = LocalDateTime.now();
        PresenceEntity entity = new PresenceEntity();
        entity.setPerson(person);
        entity.setSession(session);
        entity.setApprovedBy(approved);
        entity.setApprovedDate(approvedDate);
        return entity;
    }

    public PresenceEntity insertOne(PresenceEntity toSave) {
        PresenceEntity saved = presenceRepository.save(toSave);
        return saved;
    }

    public List<PresenceEntity> insertMany(
            int amount,
            List<PersonEntity> persons,
            List<SessionEntity> sessions,
            List<PersonEntity> approveds
    ) {
        List<PresenceEntity> entities = new ArrayList<>();
        Integer indexPerson = random.nextInt(0, persons.size());
        Integer indexSession = random.nextInt(0, sessions.size());
        Integer indexApproved = random.nextInt(0, approveds.size());
        for (int i=0; i<amount; i++) {
            PresenceEntity entity = this.makeFakeEntity(
                    persons.get(indexPerson),
                    sessions.get(indexSession),
                    approveds.get(indexApproved)
            );
            entities.add(this.insertOne(entity));
        }
        return entities;
    }

}

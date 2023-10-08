package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.repository.VoluntaryRepository;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtil;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.GeneretorCpf;
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
public class VoluntaryFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private VoluntaryRepository voluntaryRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    GeneretorCpf generetorCpf;

    @Autowired
    public VoluntaryFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public VoluntaryEntity makeFakeEntity() {
        FindRegisterRandom<SocialActionEntity> findSocialRandom = new FindRegisterRandom<SocialActionEntity>(entityManager);
        List<SocialActionEntity> socialActions = findSocialRandom.execute("social_action", 1, SocialActionEntity.class);
        FindRegisterRandom<PersonEntity> findPersonRandom = new FindRegisterRandom<PersonEntity>(entityManager);
        List<PersonEntity> persons = findPersonRandom.execute("person", 2, PersonEntity.class);
        LocalDateTime approvedDate = LocalDateTime.now();
        StatusEnum statusEnum = new EnumUtil<StatusEnum>().getRandomValue(StatusEnum.class);
        int score = random.nextInt(11); // Gere um número aleatório no intervalo de 0 a 10
        VoluntaryEntity voluntaryEntity = new VoluntaryEntity();
        voluntaryEntity.setObservation(faker.lorem().sentence());
        voluntaryEntity.setPerson(persons.get(0));
        voluntaryEntity.setSocialAction(socialActions.get(0));
        voluntaryEntity.setApprovedBy(persons.get(1));
        voluntaryEntity.setApprovedDate(approvedDate);
        voluntaryEntity.setFeedbackVoluntary(faker.lorem().sentence());
        voluntaryEntity.setFeedbackScoreVoluntary(score);
        voluntaryEntity.setStatus(statusEnum);
        return voluntaryEntity;
    }

    public VoluntaryEntity insertOne(VoluntaryEntity toSave) {
        VoluntaryEntity saved = voluntaryRepository.save(toSave);
        return saved;
    }

    public List<VoluntaryEntity> insertMany(int amount) {
        List<VoluntaryEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            VoluntaryEntity entity = this.makeFakeEntity();
            entities.add(this.insertOne(entity));
        }
        return entities;
    }
    
}

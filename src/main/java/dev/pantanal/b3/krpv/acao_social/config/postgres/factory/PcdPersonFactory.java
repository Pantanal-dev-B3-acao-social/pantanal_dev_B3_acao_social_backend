package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.PcdPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.repository.PcdPersonRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
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

@Component
public class PcdPersonFactory {

    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private PcdPersonRepository pcdPersonRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    public PcdPersonFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PcdPersonEntity makeFakeEntity() {
        PcdPersonEntity entity = new PcdPersonEntity();
        FindRegisterRandom<PersonEntity> findRegisterRandomPerson = new FindRegisterRandom<PersonEntity>(entityManager);
        List<PersonEntity> person = findRegisterRandomPerson.execute("person", 1, PersonEntity.class);
        FindRegisterRandom<PcdEntity> findRegisterRandomPcd = new FindRegisterRandom<PcdEntity>(entityManager);
        List<PcdEntity> pcd = findRegisterRandomPcd.execute("pcd", 1, PcdEntity.class);
        entity.setPcd(pcd.get(0));
        entity.setPerson(person.get(0));
        return entity;
    }

    public PcdPersonEntity insertOne(PcdPersonEntity toSave) {
        PcdPersonEntity saved = pcdPersonRepository.save(toSave);
        return saved;
    }

    public List<PcdPersonEntity> insertMany(
            int amount
    ) {
        List<PcdPersonEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            PcdPersonEntity entity = this.makeFakeEntity();
            entities.add(this.insertOne(entity));
        }
        return entities;
    }

}

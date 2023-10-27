package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.repository.PcdRepository;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCnpj;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PcdFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private PcdRepository pcdRepository;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    public PcdFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PcdEntity makeFakeEntity() {
        PcdEntity pcdEntity = new PcdEntity();
        pcdEntity.setName(faker.name().toString());
        pcdEntity.setObservation(faker.lorem().sentence());
        pcdEntity.setCode(faker.name().toString().toUpperCase());
        pcdEntity.setAcronym(faker.name().toString());
        return pcdEntity;
    }

    public PcdEntity insertOne(PcdEntity toSave) {
        PcdEntity saved = pcdRepository.save(toSave);
        return saved;
    }

    public List<PcdEntity> insertMany(int amount) {
        List<PcdEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            PcdEntity entity = this.makeFakeEntity();
            entities.add(this.insertOne(entity));
        }
        return entities;
    }

}

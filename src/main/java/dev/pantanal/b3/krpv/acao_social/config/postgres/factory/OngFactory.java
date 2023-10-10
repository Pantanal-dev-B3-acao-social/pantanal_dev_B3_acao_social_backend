package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtils;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCnpj;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class OngFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private OngRepository ongRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    GeneratorCnpj generatorCnpj;
    @Autowired
    PersonFactory personFactory;

    @Autowired
    public OngFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OngEntity makeFakeEntity() {
        StatusEnum caseEnum = new EnumUtils<StatusEnum>().getRandomValue(StatusEnum.class);
        FindRegisterRandom<PersonEntity> findRegisterRandom = new FindRegisterRandom<PersonEntity>(entityManager);
        List<PersonEntity> person = findRegisterRandom.execute("person", 1, PersonEntity.class);
        String cnpj = generatorCnpj.cnpj(true);
        OngEntity ongEntity = new OngEntity();
        ongEntity.setName(faker.lorem().sentence());
        ongEntity.setCnpj(cnpj);
        ongEntity.setResponsibleEntity(person.get(0));
        ongEntity.setStatus(caseEnum);
        return ongEntity;
    }

    public OngEntity insertOne(OngEntity toSave) {
        OngEntity saved = ongRepository.save(toSave);
        return saved;
    }

    public List<OngEntity> insertMany(int amount) {
        List<OngEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            OngEntity entity = this.makeFakeEntity();
            entities.add(this.insertOne(entity));
        }
        return entities;
    }
    
}

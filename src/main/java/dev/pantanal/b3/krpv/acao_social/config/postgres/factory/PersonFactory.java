package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtil;
import dev.pantanal.b3.krpv.acao_social.utils.GeraCNPJ;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class PersonFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    GeraCNPJ geradorDeCNPJ;

    @Autowired
    public PersonFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PersonEntity makeFakeEntity(UUID userId) {
        LocalDateTime dataBirth = LocalDateTime.now();
        StatusEnum statusEnum = new EnumUtil<StatusEnum>().getRandomValue(StatusEnum.class);
        String cnpj = geradorDeCNPJ.cnpj(true);
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(faker.lorem().sentence());
        personEntity.setDateBirth(dataBirth);
        personEntity.setCpf(cnpj);
        personEntity.setUserId(userId);
        personEntity.setStatus(statusEnum);
        return personEntity;
    }

    public PersonEntity insertOne(PersonEntity toSave) {
        PersonEntity saved = personRepository.save(toSave);
        return saved;
    }

    public List<PersonEntity> insertMany(int amount, UUID userId) {
        List<PersonEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            PersonEntity entity = this.makeFakeEntity(userId);
            entities.add(this.insertOne(entity));
        }
        return entities;
    }
    
}

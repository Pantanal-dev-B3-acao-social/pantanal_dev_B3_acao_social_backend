package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.InterestEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.repository.InterestRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
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
public class InterestFactory {

    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    GeneretorCpf generetorCpf;

    @Autowired
    public InterestFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InterestEntity makeFakeEntity(
            PersonEntity person,
            CategoryEntity category
    ) {
        Integer index = random.nextInt(0, 10);
        LocalDateTime Date = LocalDateTime.now();
        InterestEntity entity = new InterestEntity();
        entity.setPerson(person);
        entity.setCategory(category);

        return entity;
    }

    public InterestEntity insertOne(InterestEntity toSave) {
        InterestEntity saved = interestRepository.save(toSave);
        return saved;
    }

    public List<InterestEntity> insertMany(
            int amount,
            List<PersonEntity> persons,
            List<CategoryEntity> categories
    ) {
        List<InterestEntity> entities = new ArrayList<>();
        Integer indexPerson = random.nextInt(0, persons.size());
        Integer indexSession = random.nextInt(0, categories.size());

        for (int i=0; i<amount; i++) {
            InterestEntity entity = this.makeFakeEntity(
                    persons.get(indexPerson),
                    categories.get(indexSession)
            );
            entities.add(this.insertOne(entity));
        }
        return entities;
    }

}

package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.category.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class CategoryFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private GeneratorCode generatorCode;

    @Autowired
    public CategoryFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private FindRegisterRandom findRegisterRandom;

    public CategoryEntity makeFakeEntity() {
        UUID createBy = UUID.randomUUID();
        UUID deleteBy = null;
        UUID lastModifiedBy = null;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        String name = faker.name().fullName();
        String code = generatorCode.execute(name);
        return new CategoryEntity(
                1L,
                UUID.randomUUID(),
                name,
                faker.lorem().sentence(),
                code,
                createBy,
                lastModifiedBy,
                createdDate,
                lastModifiedDate,
                null,
                deleteBy
        );
    }

    public CategoryEntity insertOne(CategoryEntity toSave) {
        CategoryEntity saved = categoryRepository.save(toSave);
        return saved;
    }

    public List<CategoryEntity> insertMany(int amount) {
        List<CategoryEntity> socials = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            CategoryEntity socialActionEntity = this.makeFakeEntity();
            socials.add(this.insertOne(socialActionEntity));
        }
        return socials;
    }
}
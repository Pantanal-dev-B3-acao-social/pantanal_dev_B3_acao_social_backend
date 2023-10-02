package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
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

    public CategoryEntity makeFakeEntity(CategoryGroupEntity groupEntity) {
        UUID createBy = UUID.randomUUID();
        LocalDateTime createdDate = LocalDateTime.now();
        String name = faker.name().fullName();
        String code = generatorCode.execute(name);
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setVersion(1L);
        categoryEntity.setId(UUID.randomUUID());
        categoryEntity.setName(name);
        categoryEntity.setDescription(faker.lorem().sentence());
        categoryEntity.setCode(code);
        categoryEntity.setCreatedBy(createBy);
        categoryEntity.setCreatedDate(createdDate);
        categoryEntity.setCategoryGroup(groupEntity);
        return categoryEntity;
    }

    public CategoryEntity insertOne(CategoryEntity toSave) {
        CategoryEntity saved = categoryRepository.save(toSave);
        return saved;
    }

    public List<CategoryEntity> insertMany(int amount, List<CategoryGroupEntity> groupEntities) {
        List<CategoryEntity> entities = new ArrayList<>();
        Integer indexGroup = random.nextInt(groupEntities.size());
        CategoryGroupEntity groupEntity = groupEntities.get(indexGroup);
        for (int i=0; i<amount; i++) {
            CategoryEntity entity = this.makeFakeEntity(groupEntity);
            entities.add(this.insertOne(entity));
        }
        return entities;
    }
}

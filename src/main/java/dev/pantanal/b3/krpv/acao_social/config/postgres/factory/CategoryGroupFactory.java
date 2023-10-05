package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums.VisibilityCategoryGroupEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository.CategoryGroupRepository;
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
public class CategoryGroupFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private CategoryGroupRepository categoryGroupRepository;
    @Autowired
    private GeneratorCode generatorCode;
    @Autowired
    public CategoryGroupFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CategoryGroupEntity makeFakeEntity(String nameSuggestion, String descriptionSuggestion) {
        String nameFake = faker.name().fullName();
        String descriptionFake = faker.lorem().sentence();
        String name = nameSuggestion == null ? nameFake : nameSuggestion;
        String description = descriptionSuggestion == null ? descriptionFake : descriptionSuggestion;
        String code = generatorCode.execute(name);
        CategoryGroupEntity categoryGroupEntity = new CategoryGroupEntity();
        categoryGroupEntity.setVersion(1L);
        categoryGroupEntity.setName(name);
        categoryGroupEntity.setDescription(description);
        categoryGroupEntity.setCode(code);
        categoryGroupEntity.setVisibility(VisibilityCategoryGroupEnum.PUBLIC_EXTERNALLY);
        return categoryGroupEntity;
    }

    public CategoryGroupEntity insertOne(CategoryGroupEntity toSave) {
        CategoryGroupEntity saved = categoryGroupRepository.save(toSave);
        return saved;
    }

    public List<CategoryGroupEntity> insertMany(int amount) {
        List<CategoryGroupEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            CategoryGroupEntity entity = this.makeFakeEntity(null, null);
            entities.add(this.insertOne(entity));
        }
        return entities;
    }
}

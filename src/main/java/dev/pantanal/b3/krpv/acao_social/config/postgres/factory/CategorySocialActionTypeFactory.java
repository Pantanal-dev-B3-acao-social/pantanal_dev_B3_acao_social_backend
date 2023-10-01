package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategorySocialActionTypePostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class CategorySocialActionTypeFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    @Autowired
    private CategorySocialActionTypePostgresRepository repository;
    private EntityManager entityManager;

    public CategorySocialActionTypeFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CategorySocialActionTypeEntity makeFakeEntity(
            SocialActionEntity socialActionEntity,
            CategoryEntity categoryEntity
    ) {
        UUID createBy = UUID.randomUUID();
        UUID deleteBy = null;
        UUID lastModifiedBy = null;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        FindRegisterRandom<CategoryEntity> findRegisterRandomCategory = new FindRegisterRandom<CategoryEntity>(entityManager);
        List<CategoryEntity> categories = findRegisterRandomCategory.execute("category", 1, CategoryEntity.class);
//        Optional<CategoryEntity> firstCategory = categories.isEmpty() ? null : Optional.of(categories.get(0));
        FindRegisterRandom<SocialActionEntity> findRegisterRandomSocial = new FindRegisterRandom<SocialActionEntity>(entityManager);
        List<SocialActionEntity> socialActions = findRegisterRandomSocial.execute("social_action", 1, SocialActionEntity.class);
//        Optional<SocialActionEntity> firstSocial = socialActions.isEmpty() ? null : Optional.of(socialActions.get(0));
        CategorySocialActionTypeEntity created = new CategorySocialActionTypeEntity(
                1L,
                null,
                categoryEntity == null ? categories.get(0) : categoryEntity, // categories.get(0), // firstCategory.orElseThrow(() -> new IllegalStateException("Categoria ausente")),
                socialActionEntity == null ? socialActions.get(0) : socialActionEntity, // firstSocial.orElseThrow(() -> new IllegalStateException("Social Action ausente")),
                createBy,
                lastModifiedBy,
                createdDate,
                lastModifiedDate,
                null,
                deleteBy
        );
        return created;
    }

    public List<CategorySocialActionTypeEntity> insertManyFakeEntities(
            SocialActionEntity socialActionEntity,
            CategoryEntity category,
            Integer amount
    ) {
        List<CategorySocialActionTypeEntity> categorySocialActionTypeEntities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            CategorySocialActionTypeEntity entity = this.makeFakeEntity(socialActionEntity, category);
            categorySocialActionTypeEntities.add(this.insertOne(entity));
        }
        return categorySocialActionTypeEntities;
    }

    public CategorySocialActionTypeEntity insertOne(CategorySocialActionTypeEntity toSave) {
        CategorySocialActionTypeEntity saved = repository.save(toSave);
        return saved;
    }

}

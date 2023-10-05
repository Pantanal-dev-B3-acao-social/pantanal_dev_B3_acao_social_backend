package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategorySocialActionTypePostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.SocialActionDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class SocialActionFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private SocialActionRepository socialActionRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    public SocialActionFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SocialActionEntity makeFakeEntity(List<UUID> forCategoryTypeIds,  List<UUID> forCategoryLevelIds) {
        SocialActionEntity socialCreated = new SocialActionEntity();
        socialCreated.setVersion(1L);
        socialCreated.setName(faker.name().fullName());
        socialCreated.setDescription(faker.lorem().sentence());
        for (UUID categoryId : forCategoryTypeIds) {
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId);
            if (categoryEntity != null) {
                CategorySocialActionTypeEntity typeCategory = new CategorySocialActionTypeEntity();
                typeCategory.setCategoryEntity(categoryEntity);
                typeCategory.setSocialActionEntity(socialCreated);
                socialCreated.getCategorySocialActionTypeEntities().add(typeCategory);
            }
        }
// TODO: implementar LEVEL
        //        socialCreated.setCategorySocialActionLevelEntities(forCategoryLevelIds);
                // SessionEntity
                // string ongId = findOneRandom("ong");
                // string levelId = findOneRandom("category_project_level");
                // string typeId = findOneRandom("category_project_type");
        return socialCreated;
    }

    public String findOneRandom(String table) {
        String sql = "SELECT * FROM "+ table +" ORDER BY RANDOM() LIMIT 1";
        try {
            String id = jdbcTemplate.queryForObject(sql, String.class);
            return id;
        } catch (EmptyResultDataAccessException e) {
            // Trate o caso em que a tabela está vazia
            // TODO: lançar exceção
            return null;
        }
    }

    public SocialActionEntity insertOne(SocialActionEntity toSave) {
        SocialActionEntity socialActionEntity = socialActionRepository.save(toSave);
        return socialActionEntity;
//        return socialActionRepository.findById(socialActionEntity.getId());
    }

    public List<SocialActionEntity> insertMany(int amount, List<UUID> forCategoryTypeIds, List<UUID> forCategoryLevelIds) {
        List<SocialActionEntity> socials = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            SocialActionEntity socialActionEntity = this.makeFakeEntity(forCategoryTypeIds, forCategoryLevelIds);
            socials.add(this.insertOne(socialActionEntity));
        }
        return socials;
    }
}

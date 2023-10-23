package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionLevelEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public SocialActionEntity makeFakeEntity(List<UUID> categoryTypesIds, List<UUID> categoryLevelsIds) {
        SocialActionEntity socialCreated = new SocialActionEntity();
        socialCreated.setVersion(1L);
        socialCreated.setName(faker.name().fullName());
        socialCreated.setDescription(faker.lorem().sentence());
//        FindRegisterRandom<OngEntity> findOngRandom = new FindRegisterRandom<OngEntity>(entityManager);
//        List<OngEntity> ong = findOngRandom.execute("ong", 1, OngEntity.class);
//        socialCreated.setOng(ong.get(0)); //criar ONG
        List<CategorySocialActionTypeEntity> categoryTypesList = new ArrayList<>();
        List<CategorySocialActionLevelEntity> categoryLevelsList = new ArrayList<>();
        for (int i = 0; i < categoryTypesIds.size(); i++){
            //Criar Tipo de ação social
            CategorySocialActionTypeEntity categorySocialActionTypeEntity = new CategorySocialActionTypeEntity();
            CategoryEntity categoryTypeEntity = categoryRepository.findById(categoryTypesIds.get(i));
            categorySocialActionTypeEntity.setCategoryEntity(categoryTypeEntity);
            categorySocialActionTypeEntity.setSocialActionEntity(socialCreated);
            socialCreated.getCategorySocialActionTypeEntities().add(categorySocialActionTypeEntity);

            //Adicionar a respectiva lista
//            categoryTypesList.add(categorySocialActionTypeEntity);

        }
        for (int i = 0; i < categoryLevelsIds.size(); i++){
            //Criar level ação social
            CategorySocialActionLevelEntity categorySocialActionLevelEntity = new CategorySocialActionLevelEntity();
            CategoryEntity categoryLevelEntity = categoryRepository.findById(categoryLevelsIds.get(i));
            categorySocialActionLevelEntity.setCategoryEntity(categoryLevelEntity);
            categorySocialActionLevelEntity.setSocialActionEntity(socialCreated);
            //Adicionar a respectiva lista
//            categoryLevelsList.add(categorySocialActionLevelEntity);
            socialCreated.getCategorySocialActionLevelEntities().add(categorySocialActionLevelEntity);
        }
         //Verificar qual entidade de categoria realmente adicionar


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

    public List<SocialActionEntity> insertMany(int amount, List<UUID> categoriesTypesIds, List<UUID> categoryLevelsIds) {
        List<SocialActionEntity> socials = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            SocialActionEntity socialActionEntity = this.makeFakeEntity(categoriesTypesIds, categoryLevelsIds);
            socials.add(this.insertOne(socialActionEntity));
        }
        return socials;
    }

}

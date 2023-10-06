package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CategorySocialActionLevelPostgresRepository extends JpaRepository<CategorySocialActionLevelEntity, UUID> {

    List<CategorySocialActionLevelEntity> findBySocialActionEntityId(UUID socialActionId);

}

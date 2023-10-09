package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategorySocialActionTypePostgresRepository extends JpaRepository<CategorySocialActionTypeEntity, UUID> {

    @Query("SELECT c FROM CategorySocialActionType c WHERE c.socialActionEntity.id = :socialActionId")
    List<CategorySocialActionTypeEntity> findBySocialActionId(@Param("socialActionId") UUID socialActionId);


}

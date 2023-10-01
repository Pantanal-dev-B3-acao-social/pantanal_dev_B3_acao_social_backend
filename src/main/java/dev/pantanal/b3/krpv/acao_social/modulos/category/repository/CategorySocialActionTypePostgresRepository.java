package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategorySocialActionTypePostgresRepository extends JpaRepository<CategorySocialActionTypeEntity, UUID> {}

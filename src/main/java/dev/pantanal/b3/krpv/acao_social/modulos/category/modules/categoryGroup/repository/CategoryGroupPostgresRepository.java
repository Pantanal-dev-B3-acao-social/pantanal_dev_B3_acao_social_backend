package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategoryGroupPostgresRepository extends JpaRepository<CategoryGroupEntity, UUID> {

    Page<CategoryGroupEntity> findAll(Specification<CategoryGroupEntity> spec, Pageable pageable);

}

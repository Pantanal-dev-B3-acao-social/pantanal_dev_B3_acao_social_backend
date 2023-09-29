package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.category.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategoryPostgresRepository extends JpaRepository<CategoryEntity, UUID> {

    Page<CategoryEntity> findAll(Specification<CategoryEntity> spec, Pageable pageable);

}

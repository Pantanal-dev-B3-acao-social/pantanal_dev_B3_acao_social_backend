package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class CategoryGroupRepository {

    private final CategoryGroupPostgresRepository categoryGroupPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public CategoryGroupEntity findById(UUID id) {
        CategoryGroupEntity categoryGroupEntity = categoryGroupPostgresRepository.findById(id).orElse(null);
        return categoryGroupEntity;
    }

    public Page<CategoryGroupEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        throw new UnsupportedOperationException("função ainda não foi implementada");
    }

    public CategoryGroupEntity save(CategoryGroupEntity obj) {
        CategoryGroupEntity categoryGroupEntity = categoryGroupPostgresRepository.save(obj);
        return categoryGroupEntity;
    }

    @Transactional
    public CategoryGroupEntity update(CategoryGroupEntity obj) {
        CategoryGroupEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        CategoryGroupEntity objEntity = findById(id);
        categoryGroupPostgresRepository.delete(objEntity);
    }
}

package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
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
public class CategoryRepository {

    private final CategoryPostgresRepository categoryPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public CategoryEntity findById(UUID id) {
        CategoryEntity categoryEntity = categoryPostgresRepository.findById(id).orElse(null);
        return categoryEntity;
    }

    public Page<CategoryEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        throw new UnsupportedOperationException("função ainda não foi implementada");
    }

    public CategoryEntity save(CategoryEntity obj) {
        CategoryEntity categoryEntity = categoryPostgresRepository.save(obj);
        return categoryEntity;
    }

    @Transactional
    public CategoryEntity update(CategoryEntity obj) {
        CategoryEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        CategoryEntity objEntity = findById(id);
        categoryPostgresRepository.delete(objEntity);
    }
}

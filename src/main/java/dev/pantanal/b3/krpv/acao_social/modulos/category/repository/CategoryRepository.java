package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.QInvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.QCategoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCategoryEntity qEntity = QCategoryEntity.categoryEntity;
        List<CategoryEntity> results = queryFactory.selectFrom(qEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qEntity)
                .from(qEntity)
                .where(predicate)
                .fetch()
                .stream().count();
        return new PageImpl<>(results,pageable,total);
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

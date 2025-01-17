package dev.pantanal.b3.krpv.acao_social.modulos.pcd.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.QPcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.QPcdEntity;
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
public class PcdRepository {

    private final PcdPostgresRepository pcdPostgresRepository ;

    @PersistenceContext
    private final EntityManager entityManager;

    public PcdEntity findById(UUID id) {
        PcdEntity entity = pcdPostgresRepository.findById(id).orElse(null);
        return entity;
    }

    public Page<PcdEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QPcdEntity qEntity = QPcdEntity.pcdEntity;
        List<PcdEntity> results = queryFactory.selectFrom(qEntity)
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

    public PcdEntity save(PcdEntity obj) {
        PcdEntity entity = pcdPostgresRepository.save(obj);
        return entity;
    }

    @Transactional
    public PcdEntity update(PcdEntity obj) {
        PcdEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        pcdPostgresRepository.deleteById(id);
    }
}

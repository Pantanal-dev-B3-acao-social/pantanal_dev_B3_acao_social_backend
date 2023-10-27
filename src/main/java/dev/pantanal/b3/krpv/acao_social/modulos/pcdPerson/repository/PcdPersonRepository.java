package dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.PcdPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.QPcdPersonEntity;
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
public class PcdPersonRepository {

    private final PcdPersonPostgresRepository pcdPostgresRepository ;

    @PersistenceContext
    private final EntityManager entityManager;

    public PcdPersonEntity findById(UUID id) {
        PcdPersonEntity entity = pcdPostgresRepository.findById(id).orElse(null);
        return entity;
    }

    public Page<PcdPersonEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QPcdPersonEntity qEntity = QPcdPersonEntity.pcdPersonEntity;
        List<PcdPersonEntity> results = queryFactory.selectFrom(qEntity)
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

    public PcdPersonEntity save(PcdPersonEntity obj) {
        PcdPersonEntity entity = pcdPostgresRepository.save(obj);
        return entity;
    }

    @Transactional
    public PcdPersonEntity update(PcdPersonEntity obj) {
        PcdPersonEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        pcdPostgresRepository.deleteById(id);
    }
}

package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.QVoluntaryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryEntity;
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
public class VoluntaryRepository {

    private final VoluntaryPostgresRepository voluntaryPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public VoluntaryEntity findById(UUID id) {
        VoluntaryEntity sessionEntity = voluntaryPostgresRepository.findById(id).orElse(null);
        return sessionEntity;
    }

    public Page<VoluntaryEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QVoluntaryEntity qEntity = QVoluntaryEntity.voluntaryEntity;
        List<VoluntaryEntity> results = queryFactory.selectFrom(qEntity)
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

    public VoluntaryEntity save(VoluntaryEntity obj) {
        VoluntaryEntity sessionEntity = voluntaryPostgresRepository.save(obj);
        return sessionEntity;
    }

    @Transactional
    public VoluntaryEntity update(VoluntaryEntity obj) {
        VoluntaryEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        voluntaryPostgresRepository.deleteById(id);
    }
}

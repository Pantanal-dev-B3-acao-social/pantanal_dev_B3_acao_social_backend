package dev.pantanal.b3.krpv.acao_social.modulos.presence.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.QPresenceEntity;
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
public class PresenceRepository {

    private final PresencePostgresRepository presencePostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public PresenceEntity findById(UUID id) {
        PresenceEntity sessionEntity = presencePostgresRepository.findById(id).orElse(null);
        return sessionEntity;
    }

    public Page<PresenceEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QPresenceEntity qEntity = QPresenceEntity.presenceEntity;
        List<PresenceEntity> results = queryFactory.selectFrom(qEntity)
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

    public PresenceEntity save(PresenceEntity obj) {
        PresenceEntity sessionEntity = presencePostgresRepository.save(obj);
        return sessionEntity;
    }

    @Transactional
    public PresenceEntity update(PresenceEntity obj) {
        PresenceEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        presencePostgresRepository.deleteById(id);
    }
}

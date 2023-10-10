package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Repository
public class SocialActionRepository {

    private final SocialActionPostgresRepository postgresSocialActionRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public SocialActionEntity findByIdWithCategories(UUID id) {
        return entityManager.createQuery(
            "SELECT sa FROM SocialAction sa LEFT JOIN FETCH sa.categorySocialActionTypeEntities WHERE sa.id = :id",
            SocialActionEntity.class
        )
            .setParameter("id", id)
            .getSingleResult();
    }

    public SocialActionEntity save(SocialActionEntity obj) {
        SocialActionEntity socialActionEntity = postgresSocialActionRepository.save(obj);
        return socialActionEntity;
    }


    public Page<SocialActionEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QSocialActionEntity qEntity = QSocialActionEntity.socialActionEntity;
        List<SocialActionEntity> results = queryFactory.selectFrom(qEntity)
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

    public SocialActionEntity findById(UUID id) {
        SocialActionEntity socialActionEntity = postgresSocialActionRepository.findById(id).orElse(null);
        return socialActionEntity;
    }

    @Transactional
    public SocialActionEntity update(SocialActionEntity obj) {
        SocialActionEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        SocialActionEntity objEntity = findById(id);
        postgresSocialActionRepository.delete(objEntity);
    }

}

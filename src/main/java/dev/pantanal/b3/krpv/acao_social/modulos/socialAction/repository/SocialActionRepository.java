package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Component;
// TODO: verificar se vamos usar jakarta ou javax
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
        // TODO
//        List<SocialActionEntity> results = queryFactory.select(
//                        Projections.bean(
//                                SocialActionEntity.class,
//                                qEntity.id,
//                                qEntity.name,
//                                qEntity.description
//                        )
//        )
//            .from(qEntity)
//            .where(predicate)
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();
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

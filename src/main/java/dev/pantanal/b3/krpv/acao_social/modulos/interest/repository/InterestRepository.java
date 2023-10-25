package dev.pantanal.b3.krpv.acao_social.modulos.interest.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.InterestEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.QInterestEntity;
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
public class InterestRepository {

    private final InterestPostgresRepository interestPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public InterestEntity findById(UUID id) {
        InterestEntity interestEntity = interestPostgresRepository.findById(id).orElse(null);
        return interestEntity;
    }

    public Page<InterestEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QInterestEntity qInterestEntity = QInterestEntity.interestEntity;
        List<InterestEntity> results = queryFactory.selectFrom(qInterestEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qInterestEntity)
                .from(qInterestEntity)
                .where(predicate)
                .fetch()
                .stream().count();

        return new PageImpl<>(results,pageable,total);
    }

    public InterestEntity save(InterestEntity obj) {
        InterestEntity interestEntity = interestPostgresRepository.save(obj);
        return interestEntity;
    }

    public void delete(UUID id) {
        interestPostgresRepository.deleteById(id);
    }
}

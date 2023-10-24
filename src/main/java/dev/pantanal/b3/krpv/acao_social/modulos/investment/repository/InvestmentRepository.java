package dev.pantanal.b3.krpv.acao_social.modulos.investment.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.InvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.QInvestmentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InvestmentRepository {

    @Autowired
    private InvestmentPostgresRepository investmentPostgresRepository;
    @PersistenceContext
    private final EntityManager entityManager;
    public InvestmentEntity save(InvestmentEntity entityObj) {
        InvestmentEntity investmentEntity = investmentPostgresRepository.save(entityObj);
        return investmentEntity;
    }

    public Page<InvestmentEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QInvestmentEntity qInvestmentEntity = QInvestmentEntity.investmentEntity;

        List<InvestmentEntity> results = queryFactory.selectFrom(qInvestmentEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qInvestmentEntity)
                .from(qInvestmentEntity)
                .where(predicate)
                .fetch()
                .stream().count();

        return new PageImpl<>(results,pageable,total);
    }

    public InvestmentEntity findById(UUID id) {
        return investmentPostgresRepository.findById(id).orElse(null);
    }

    @Transactional
    public InvestmentEntity update(InvestmentEntity obj) {
        InvestmentEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush();
        return updatedEntity;
    }

    public void delete(UUID id) {
        investmentPostgresRepository.deleteById(id);
    }
}

package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.ContractEntity;
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
public class ContractRepository {

    private final ContractPostgresRepository contractPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public ContractEntity save(ContractEntity obj) {
        ContractEntity contractEntity = contractPostgresRepository.save(obj);
        return contractEntity;
    }

    public ContractEntity findById(UUID id) {
        ContractEntity contractEntity = contractPostgresRepository.findById(id).orElse(null);
        return contractEntity;
    }
//
//    public Page<ContractEntity> findAll(Pageable pageable, BooleanExpression predicate) {
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//        QContractEntity qContractEntity = QContractEntity.contractEntity;
//        List<ContractEntity> results = queryFactory.selectFrom(qContractEntity)
//                .where(predicate)
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//        long total = queryFactory.query()
//                .select(qContractEntity)
//                .from(qContractEntity)
//                .where(predicate)
//                .fetch()
//                .stream().count();
//
//        return new PageImpl<>(results,pageable,total);
//    }
//
    public void delete(UUID id) {
        contractPostgresRepository.deleteById(id);
    }
}

package dev.pantanal.b3.krpv.acao_social.modulos.company.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.QCompanyEntity;
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
public class CompanyRepository {
    @Autowired
    private CompanyPostgresRepository companyPostgresRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public CompanyEntity save(CompanyEntity entityObj) {
        CompanyEntity companyEntity = companyPostgresRepository.save(entityObj);
        return companyEntity;

    }

    public Page<CompanyEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QCompanyEntity qCompanyEntity = QCompanyEntity.companyEntity;

        List<CompanyEntity> results = queryFactory.selectFrom(qCompanyEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qCompanyEntity)
                .from(qCompanyEntity)
                .where(predicate)
                .fetch()
                .stream().count();

        return new PageImpl<>(results,pageable,total);
    }

    public CompanyEntity findById(UUID id) {
        CompanyEntity companyEntity = companyPostgresRepository.findById(id).orElse(null);
        return companyEntity;
    }
    @Transactional
    public CompanyEntity update(CompanyEntity obj) {
        CompanyEntity companyEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return companyEntity;
    }

    public void delete(UUID id) {
        companyPostgresRepository.deleteById(id);
    }


}

package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.QOngEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class OngRepository {
    private final OngPostgresRepository ongPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public OngEntity findById(UUID id) {
        OngEntity sessionEntity = ongPostgresRepository.findById(id).orElse(null);
        return sessionEntity;
    }

    public Page<OngEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QOngEntity qEntity = QOngEntity.ongEntity;
        List<OngEntity> results = queryFactory.selectFrom(qEntity)
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

    public OngEntity save(OngEntity obj) {
        OngEntity sessionEntity = ongPostgresRepository.save(obj);
        return sessionEntity;
    }

    @Transactional
    public OngEntity update(OngEntity obj) {
        OngEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        OngEntity objEntity = findById(id);
        ongPostgresRepository.delete(objEntity);
    }

}

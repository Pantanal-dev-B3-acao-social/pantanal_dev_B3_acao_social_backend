package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import com.github.javafaker.Ong;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.category.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.QOngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Repository
public class OngRepository {
    private final OngPostgresRepository ongPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public OngEntity findById(UUID id) {
        OngEntity ongEntity = ongPostgresRepository.findById(id).orElse(null);
        return ongEntity;
    }

    public Page<OngEntity> findAll(Pageable pageable, OngParamsDto filters) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QOngEntity qOngEntity = QOngEntity.ongEntity;

        List<OngEntity> results = queryFactory.selectFrom(qOngEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qOngEntity)
                .from(qOngEntity)
                .where(predicate)
                .fetch()
                .stream().count();

        return new PageImpl<>(results,pageable,total);
    }

    public OngEntity save(OngEntity obj) {
        OngEntity ongEntity = ongPostgresRepository.save(obj);
        return ongEntity;
    }

    @Transactional
    public OngEntity update(OngEntity obj) {
        OngEntity updatedEntity = entityManager.merge(obj);
        return updatedEntity;
    }

    public void delete(UUID id) {
        OngEntity objEntity = findById(id);
        ongPostgresRepository.delete(objEntity);
    }

}

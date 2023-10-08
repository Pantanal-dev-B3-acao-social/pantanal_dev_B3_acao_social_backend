package dev.pantanal.b3.krpv.acao_social.modulos.person.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.QPersonEntity;
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
public class PersonRepository {

    private final PersonPostgresRepository personPostgresRepository ;

    @PersistenceContext
    private final EntityManager entityManager;

    public PersonEntity findById(UUID id) {
        PersonEntity entity = personPostgresRepository.findById(id).orElse(null);
        return entity;
    }

    public Page<PersonEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QPersonEntity qEntity = QPersonEntity.personEntity;
        List<PersonEntity> results = queryFactory.selectFrom(qEntity)
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

    public PersonEntity save(PersonEntity obj) {
        PersonEntity entity = personPostgresRepository.save(obj);
        return entity;
    }

    @Transactional
    public PersonEntity update(PersonEntity obj) {
        PersonEntity updatedEntity = entityManager.merge(obj);
        entityManager.flush(); // Força o Hibernate a disparar eventos JPA @PreUpdate
        return updatedEntity;
    }

    public void delete(UUID id) {
        PersonEntity objEntity = findById(id);
        personPostgresRepository.delete(objEntity);
    }
}

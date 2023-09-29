package dev.pantanal.b3.krpv.acao_social.modulos.session.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class SessionRepository {

    private final SessionPostgresRepository sessionPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public SessionEntity findById(UUID id) {
        SessionEntity sessionEntity = sessionPostgresRepository.findById(id).orElse(null);
        return sessionEntity;
    }

    public Page<SessionEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        throw new UnsupportedOperationException("função ainda não foi implementada");
    }

    public SessionEntity save(SessionEntity obj) {
        SessionEntity sessionEntity = sessionPostgresRepository.save(obj);
        return sessionEntity;
    }

    @Transactional
    public SessionEntity update(SessionEntity obj) {
        SessionEntity updatedEntity = entityManager.merge(obj);
        return updatedEntity;
    }

    public void delete(UUID id) {
        SessionEntity objEntity = findById(id);
        sessionPostgresRepository.delete(objEntity);
    }
}

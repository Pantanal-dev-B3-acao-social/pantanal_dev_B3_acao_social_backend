package dev.pantanal.b3.krpv.acao_social.modulos.session.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionPostgresRepository extends JpaRepository<SessionEntity, UUID> {

    Page<SessionEntity> findAll(Specification<SessionEntity> spec, Pageable pageable);

}

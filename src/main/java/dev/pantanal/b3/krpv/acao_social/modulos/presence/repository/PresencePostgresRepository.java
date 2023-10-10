package dev.pantanal.b3.krpv.acao_social.modulos.presence.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PresencePostgresRepository extends JpaRepository<PresenceEntity, UUID> {

    Page<PresenceEntity> findAll(Specification<PresenceEntity> spec, Pageable pageable);

}

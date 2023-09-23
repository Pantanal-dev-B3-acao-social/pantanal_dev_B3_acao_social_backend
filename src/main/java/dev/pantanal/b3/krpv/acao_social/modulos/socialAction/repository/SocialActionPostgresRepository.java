package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@Repository
public interface SocialActionPostgresRepository extends JpaRepository<SocialActionEntity, UUID> {

//    Page<SocialActionEntity> findAll(Pageable pageable, SocialActionParamsDto filters);
    Page<SocialActionEntity> findAll(Specification<SocialActionEntity> spec, Pageable pageable);

}

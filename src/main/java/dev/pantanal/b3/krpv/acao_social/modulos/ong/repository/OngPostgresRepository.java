package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

@Repository
public interface OngPostgresRepository  extends JpaRepository<OngEntity, UUID> {

    Page<OngEntity> findAll(Specification<OngEntity> spec, Pageable pageable);

}

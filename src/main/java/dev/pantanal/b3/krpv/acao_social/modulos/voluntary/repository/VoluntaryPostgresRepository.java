package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VoluntaryPostgresRepository extends JpaRepository<VoluntaryEntity, UUID> {

    Page<VoluntaryEntity> findAll(Specification<VoluntaryEntity> spec, Pageable pageable);

}

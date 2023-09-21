package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OngPostgresRepository  extends JpaRepository<OngEntity, UUID> {
}

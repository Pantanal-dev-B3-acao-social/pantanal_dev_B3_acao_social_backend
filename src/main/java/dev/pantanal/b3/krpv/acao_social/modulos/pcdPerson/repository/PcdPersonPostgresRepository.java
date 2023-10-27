package dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.PcdPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PcdPersonPostgresRepository extends JpaRepository<PcdPersonEntity, UUID> {
}

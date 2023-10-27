package dev.pantanal.b3.krpv.acao_social.modulos.pcd.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PcdPostgresRepository extends JpaRepository<PcdEntity, UUID> {
}

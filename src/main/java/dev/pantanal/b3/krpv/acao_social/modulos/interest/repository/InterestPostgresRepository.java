package dev.pantanal.b3.krpv.acao_social.modulos.interest.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.interest.InterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterestPostgresRepository extends JpaRepository<InterestEntity, UUID> {
}

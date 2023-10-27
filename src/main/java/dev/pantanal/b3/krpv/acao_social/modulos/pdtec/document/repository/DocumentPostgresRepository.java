package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentPostgresRepository extends JpaRepository<DocumentEntity, UUID> {
}

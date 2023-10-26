package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContractPostgresRepository extends JpaRepository<ContractEntity, UUID> {
}

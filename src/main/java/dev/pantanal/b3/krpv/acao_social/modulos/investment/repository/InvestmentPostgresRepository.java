package dev.pantanal.b3.krpv.acao_social.modulos.investment.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.investment.InvestmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvestmentPostgresRepository extends JpaRepository<InvestmentEntity, UUID> {
}

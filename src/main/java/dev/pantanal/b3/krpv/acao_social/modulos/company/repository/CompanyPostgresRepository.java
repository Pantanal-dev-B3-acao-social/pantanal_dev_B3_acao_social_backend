package dev.pantanal.b3.krpv.acao_social.modulos.company.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CompanyPostgresRepository extends JpaRepository<CompanyEntity, UUID> {

}

package dev.pantanal.b3.krpv.acao_social.modulos.donation.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DonationPostgresRepository extends JpaRepository<DonationEntity, UUID> {
    Page<DonationEntity> findAll(Specification<DonationEntity> spec, Pageable pageable);

}

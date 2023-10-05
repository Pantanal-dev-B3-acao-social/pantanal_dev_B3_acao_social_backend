package dev.pantanal.b3.krpv.acao_social.modulos.person.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PersonPostgresRepository extends JpaRepository<PersonEntity, UUID> {

    Page<PersonEntity> findAll(Specification<PersonEntity> spec, Pageable pageable);

}

package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
@Repository
public class OngRepository {
    private final OngPostgresRepository ongPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

}

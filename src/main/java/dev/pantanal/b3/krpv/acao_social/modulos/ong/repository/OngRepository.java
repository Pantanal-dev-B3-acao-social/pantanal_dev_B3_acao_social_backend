package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Repository
public class OngRepository {
    private final OngPostgresRepository ongPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public OngEntity findById(UUID id) {
        OngEntity ongEntity = ongPostgresRepository.findById(id).orElse(null);
        return ongEntity;
    }

    public Page<OngEntity> findAll(Pageable pageable, OngParamsDto filters) {
        throw new UnsupportedOperationException("função ainda não foi implementada");
    }

    public OngEntity save(OngEntity obj) {
        OngEntity ongEntity = ongPostgresRepository.save(obj);
        return ongEntity;
    }

    @Transactional
    public OngEntity update(OngEntity obj) {
        OngEntity updatedEntity = entityManager.merge(obj);
        return updatedEntity;
    }

    public void delete(UUID id) {
        OngEntity objEntity = findById(id);
        ongPostgresRepository.delete(objEntity);
    }

}

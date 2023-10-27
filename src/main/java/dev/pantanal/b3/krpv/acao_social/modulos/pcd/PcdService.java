package dev.pantanal.b3.krpv.acao_social.modulos.pcd;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.repository.PcdPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.repository.PcdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PcdService {
    @Autowired
    private PcdRepository pcdRepository;
    @Autowired
    private PcdPredicates pcdPredicates;

    public PcdEntity create(PcdCreateDto dataRequest) {
        PcdEntity entity = new PcdEntity();
        entity.setName(dataRequest.name());
        entity.setObservation(dataRequest.observation());
        entity.setAcronym(dataRequest.acronym());
        entity.setCode(dataRequest.code());
        PcdEntity savedObj = pcdRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        pcdRepository.delete(id);
    }

    public Page<PcdEntity> findAll(Pageable pageable, PcdParamsDto filters) {
        BooleanExpression predicate = pcdPredicates.buildPredicate(filters);
        Page<PcdEntity> objects = pcdRepository.findAll(pageable, predicate);
        return objects;
    }

    public PcdEntity findById(UUID id) {
        PcdEntity obj = pcdRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public PcdEntity update(UUID id, PcdUpdateDto request) {
        PcdEntity obj = pcdRepository.findById(id);
        if (request.name() != null) {
            obj.setName(request.name());
        }
        if (request.observation() != null) {
            obj.setObservation(request.observation());
        }
        if (request.code() != null) {
            obj.setCode(request.code());
        }
        if (request.acronym() != null) {
            obj.setAcronym(request.acronym());
        }
        PcdEntity updatedObj = pcdRepository.update(obj);
        return updatedObj;
    }

}

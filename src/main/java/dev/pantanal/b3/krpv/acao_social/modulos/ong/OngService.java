package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OngService {
    @Autowired
    private OngRepository ongRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private OngPredicates ongPredicates;

    public OngEntity create(OngCreateDto dataRequest) {
        OngEntity entity = new OngEntity();
        entity.setName(dataRequest.name());
        entity.setCnpj(dataRequest.cnpj());
        PersonEntity responsibleEntity = personRepository.findById(dataRequest.responsibleEntity());
        if (responsibleEntity == null){
            throw new ObjectNotFoundException("Responsible not found");
        }
        entity.setResponsibleEntity(responsibleEntity);
        entity.setStatus(dataRequest.status());
        OngEntity savedObj = ongRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        ongRepository.delete(id);
    }

    public Page<OngEntity> findAll(Pageable pageable, OngParamsDto filters) {
        BooleanExpression predicate = ongPredicates.buildPredicate(filters);
        Page<OngEntity> objects = ongRepository.findAll(pageable, predicate);
        return objects;
    }

    public OngEntity findById(UUID id) {
        OngEntity obj = ongRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public OngEntity update(UUID id, OngUpdateDto request) {
        OngEntity obj = ongRepository.findById(id);
        if (request.name() != null) {
            obj.setName(request.name());
        }
        if (request.status() != null) {
            obj.setStatus(request.status());
        }
        if (request.cnpj() != null) {
            obj.setCnpj(request.cnpj());
        }
        if (request.responsibleEntity() != null) {
            PersonEntity resposible = personRepository.findById(request.responsibleEntity());
            obj.setResponsibleEntity(resposible);
        }
        OngEntity updatedObj = ongRepository.update(obj);
        return updatedObj;
    }

}

package dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.repository.PcdRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request.PcdPersonCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request.PcdPersonParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request.PcdPersonUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.repository.PcdPersonPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.repository.PcdPersonRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PcdPersonService {
    @Autowired
    private PcdPersonRepository pcdPersonRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PcdRepository pcdRepository;
    @Autowired
    private PcdPersonPredicates pcdPersonPredicates;

    public PcdPersonEntity create(PcdPersonCreateDto dataRequest) {
        PcdPersonEntity entity = new PcdPersonEntity();
        PersonEntity person = personRepository.findById(dataRequest.person());
        PcdEntity pcd = pcdRepository.findById(dataRequest.pcd());
        if (person != null && pcd != null){
            entity.setPcd(pcd);
            entity.setPerson(person);
        }
        else{
            throw new ObjectNotFoundException("Not found");
        }
        PcdPersonEntity savedObj = pcdPersonRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        pcdPersonRepository.delete(id);
    }

    public Page<PcdPersonEntity> findAll(Pageable pageable, PcdPersonParamsDto filters) {
        BooleanExpression predicate = pcdPersonPredicates.buildPredicate(filters);
        Page<PcdPersonEntity> objects = pcdPersonRepository.findAll(pageable, predicate);
        return objects;
    }

    public PcdPersonEntity findById(UUID id) {
        PcdPersonEntity obj = pcdPersonRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public PcdPersonEntity update(UUID id, PcdPersonUpdateDto request) {
        PcdPersonEntity obj = pcdPersonRepository.findById(id);
        if (obj == null){
            throw new ObjectNotFoundException("Not found");
        }
        if (request.person() != null) {
            PersonEntity person = personRepository.findById(request.person());
            obj.setPerson(person);
        }
        if (request.pcd() != null) {
            PcdEntity pcd = pcdRepository.findById(request.pcd());
            obj.setPcd(pcd);
        }

        PcdPersonEntity updatedObj = pcdPersonRepository.update(obj);
        return updatedObj;
    }

}

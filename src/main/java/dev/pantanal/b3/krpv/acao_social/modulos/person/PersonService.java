package dev.pantanal.b3.krpv.acao_social.modulos.person;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonPredicates personPredicates;


    public PersonEntity create(PersonCreateDto dataRequest) {
        PersonEntity entity = new PersonEntity();
        entity.setName(dataRequest.name());
        entity.setDateBirth(dataRequest.dateBirth());
        entity.setCpf(dataRequest.cpf());
        entity.setStatus(dataRequest.status());
        entity.setUserId(dataRequest.userId());
        PersonEntity savedObj = personRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        personRepository.delete(id);
    }

    public Page<PersonEntity> findAll(Pageable pageable, PersonParamsDto filters) {
        BooleanExpression predicate = personPredicates.buildPredicate(filters);
        Page<PersonEntity> objects = personRepository.findAll(pageable, predicate);
        return objects;
    }

    public PersonEntity findById(UUID id) {
        PersonEntity obj = personRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public PersonEntity update(UUID id, PersonUpdateDto request) {
        PersonEntity obj = personRepository.findById(id);
        if (request.name() != null) {
            obj.setName(request.name());
        }
        if (request.dateBirth() != null) {
            obj.setDateBirth(request.dateBirth());
        }
        if (request.status() != null) {
            obj.setStatus(request.status());
        }
        PersonEntity updatedObj = personRepository.update(obj);
        return updatedObj;
    }

}

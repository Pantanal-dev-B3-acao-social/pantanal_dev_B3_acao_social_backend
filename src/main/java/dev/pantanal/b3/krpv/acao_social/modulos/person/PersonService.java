package dev.pantanal.b3.krpv.acao_social.modulos.person;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthController;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.user.KeycloakUser;
import dev.pantanal.b3.krpv.acao_social.modulos.user.UserService;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.CredentialDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonPredicates personPredicates;
    @Autowired
    UserService userService;


    public PersonEntity create(PersonCreateDto dataRequest, String tokenUserLogged) {
        KeycloakUser keycloakUser = dataRequest.keycloakUser();
        List<CredentialDto> credentialList = keycloakUser.getCredentials().values().stream()
                .map(value -> new CredentialDto("password", value, false))
                .collect(Collectors.toList());
        UserCreateDto userCreateDto = new UserCreateDto(
                keycloakUser.getUsername(),
                true,
                keycloakUser.getEmail(),
                keycloakUser.getFirstName(),
                keycloakUser.getLastName(),
                credentialList
        );
        UUID userId = userService.create(userCreateDto, tokenUserLogged);
        PersonEntity entity = new PersonEntity();
        entity.setName(dataRequest.name());
        entity.setDateBirth(dataRequest.dateBirth());
        entity.setCpf(dataRequest.cpf());
        entity.setStatus(dataRequest.status());
        entity.setUserId(userId);
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

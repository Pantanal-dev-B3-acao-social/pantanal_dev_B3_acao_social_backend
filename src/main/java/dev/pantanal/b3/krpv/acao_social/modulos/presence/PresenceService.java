package dev.pantanal.b3.krpv.acao_social.modulos.presence;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.request.PresenceCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.request.PresenceParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.repository.PresencePredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.repository.PresenceRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PresenceService {

    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PresencePredicates presencePredicates;

    @Autowired
    private UserService userService;

    public PresenceEntity create(PresenceCreateDto dataRequest) {
        SessionEntity sessionEntity = sessionRepository.findById(dataRequest.session());
        PersonEntity personEntity = personRepository.findById(dataRequest.person());
        PersonEntity approvedBy = personRepository.findById(dataRequest.approvedBy());
        if (approvedBy == null || personEntity == null || sessionEntity == null || dataRequest.approvedDate() == null) {
            throw new ObjectNotFoundException("Deve estar preenchido approvedBy, personEntity, sessionEntity e approvedDate.");
        }
        PresenceEntity entity = new PresenceEntity();
        entity.setPerson(personEntity);
        entity.setSession(sessionEntity);
        entity.setEngagementScore(sessionEntity.getEngagementScore());
        entity.setApprovedBy(approvedBy);
        entity.setApprovedDate(dataRequest.approvedDate());
        PresenceEntity savedObj = presenceRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        presenceRepository.delete(id);
    }

    public Page<PresenceEntity> findAll(Pageable pageable, PresenceParamsDto filters) {
        BooleanExpression predicate = presencePredicates.buildPredicate(filters);
        Page<PresenceEntity> objects = presenceRepository.findAll(pageable, predicate);
        return objects;
    }

    public PresenceEntity findById(UUID id) {
        PresenceEntity obj = presenceRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

}

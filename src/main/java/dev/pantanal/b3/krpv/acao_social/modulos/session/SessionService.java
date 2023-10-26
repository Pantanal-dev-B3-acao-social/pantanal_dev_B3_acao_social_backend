package dev.pantanal.b3.krpv.acao_social.modulos.session;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request.SessionCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request.SessionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request.SessionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.session.repository.SessionRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SocialActionRepository socialActionRepository;
    @Autowired
    private SessionPredicates sessionPredicates;
    @Autowired
    private GeneratorCode generatorCode;

    public SessionEntity create(SessionCreateDto dataRequest) {
        SessionEntity entity = new SessionEntity();
        SocialActionEntity socialAction = socialActionRepository.findById(dataRequest.socialAction());
        entity.setDescription(dataRequest.description());
        entity.setDateStartTime(dataRequest.dateStartTime());
        entity.setDateEndTime(dataRequest.dateEndTime());
        entity.setStatus(dataRequest.status());
        entity.setVisibility(dataRequest.visibility());
        entity.setEngagementScore(dataRequest.engagementScore());
        if (socialAction == null) {
            throw new ObjectNotFoundException("Social action not found");
        }
        // TODO:
        // entity.setLocal();dataRequest.local());
        // entity.setResources();dataRequest.resouces());
        // entity.setPresences(dataRequest.presences());
        SessionEntity savedObj = sessionRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        sessionRepository.delete(id);
    }

    public Page<SessionEntity> findAll(Pageable pageable, SessionParamsDto filters) {
        BooleanExpression predicate = sessionPredicates.buildPredicate(filters);
        Page<SessionEntity> objects = sessionRepository.findAll(pageable, predicate);
        return objects;
    }

    public SessionEntity findById(UUID id) {
        SessionEntity obj = sessionRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public SessionEntity update(UUID id, SessionUpdateDto request) {
        SessionEntity obj = sessionRepository.findById(id);
        if (request.description() != null) {
            obj.setDescription(request.description());
        }
        if (request.dateStart() != null) {
            obj.setDateStartTime(request.dateStart());
        }
        if (request.dateEnd() != null) {
            obj.setDateEndTime(request.dateEnd());
        }
        if (request.status() != null) {
            obj.setStatus(request.status());
        }
        if (request.visibility() != null) {
            obj.setVisibility(request.visibility());
        }
        SessionEntity updatedObj = sessionRepository.update(obj);
        return updatedObj;
    }

}

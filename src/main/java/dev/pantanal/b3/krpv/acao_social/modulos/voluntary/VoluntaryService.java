package dev.pantanal.b3.krpv.acao_social.modulos.voluntary;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request.VoluntaryCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request.VoluntaryParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request.VoluntaryUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.repository.VoluntaryPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.repository.VoluntaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VoluntaryService {


    @Autowired
    private VoluntaryRepository voluntaryRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SocialActionRepository socialActionRepository;
    @Autowired
    private VoluntaryPredicates voluntaryPredicates;

    public VoluntaryEntity create(VoluntaryCreateDto dataRequest) {
        PersonEntity approvedBy = personRepository.findById(dataRequest.approvedBy());
        PersonEntity voluntary = personRepository.findById(dataRequest.person());
        SocialActionEntity socialAction = socialActionRepository.findById(dataRequest.socialAction());
        VoluntaryEntity entity = new VoluntaryEntity();
        entity.setObservation(dataRequest.observation());
        entity.setSocialAction(socialAction);
        entity.setPerson(voluntary);
        entity.setStatus(dataRequest.status());
        entity.setApprovedBy(approvedBy);
        entity.setApprovedDate(dataRequest.approvedDate());
        entity.setFeedbackScoreVoluntary(dataRequest.feedbackScoreVoluntary());
        entity.setFeedbackVoluntary(dataRequest.feedbackVoluntary());
        VoluntaryEntity savedObj = voluntaryRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        voluntaryRepository.delete(id);
    }

    public Page<VoluntaryEntity> findAll(Pageable pageable, VoluntaryParamsDto filters) {
        BooleanExpression predicate = voluntaryPredicates.buildPredicate(filters);
        Page<VoluntaryEntity> objects = voluntaryRepository.findAll(pageable, predicate);
        return objects;
    }

    public VoluntaryEntity findById(UUID id) {
        VoluntaryEntity obj = voluntaryRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public VoluntaryEntity update(UUID id, VoluntaryUpdateDto request) {
        VoluntaryEntity obj = voluntaryRepository.findById(id);
        if (request.observation() != null) {
            obj.setObservation(request.observation());
        }
        if (request.approvedBy() != null) {
            obj.setApprovedBy(request.approvedBy());
        }
        if (request.approvedDate() != null) {
            obj.setApprovedDate(request.approvedDate());
        }
        if (request.status() != null) {
            obj.setStatus(request.status());
        }
        if (request.feedbackVoluntary() != null) {
            obj.setFeedbackVoluntary(request.feedbackVoluntary());
        }
        if (request.feedbackScoreVoluntary() != null) {
            obj.setFeedbackScoreVoluntary(request.feedbackScoreVoluntary());
        }
        VoluntaryEntity updatedObj = voluntaryRepository.update(obj);
        return updatedObj;
    }

}

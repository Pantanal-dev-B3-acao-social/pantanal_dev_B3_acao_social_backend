package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.stereotype.Service;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;


import java.util.UUID;

@Service
public class SocialActionService {

    @Autowired
    private SocialActionRepository socialActionRepository;

    public SocialActionEntity create(SocialActionCreateDto dataRequest) {
        SocialActionEntity entity = new SocialActionEntity();
        entity.setName(dataRequest.name());
        entity.setDescription(dataRequest.description());
        entity.setOrganizer(dataRequest.organizer());
        SocialActionEntity savedObj = socialActionRepository.save(entity);
        // lançar exceções
        return savedObj;
    }

    public void delete(UUID id) {
        socialActionRepository.delete(id);
    }

    public Page<SocialActionEntity> findAll(JwtAuthenticationToken userLogged, Pageable pageable, SocialActionParamsDto filters) {
//        var id = userLogged.getName();
        var id = userLogged.getToken().getClaim("sub");
        var name = userLogged.getToken().getClaim("preferred_username");

        QSocialActionEntity qSocialActionEntity = QSocialActionEntity.socialActionEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null){
            StringPath filterNamePath = qSocialActionEntity.name;
            predicate = predicate.and(filterNamePath.eq(filters.name()));
        }
        if (filters.description() != null){
            StringPath filterDescriptionPath = qSocialActionEntity.description;
            predicate = predicate.and(filterDescriptionPath.eq(filters.description()));
        }
        if (filters.organizer() != null){
            StringPath filterOrganizerPath = qSocialActionEntity.organizer;
            predicate = predicate.and(filterOrganizerPath.eq(filters.organizer()));
        }

        Page<SocialActionEntity> objects = socialActionRepository.findAll(pageable, predicate);
        // lançar exceções
        return objects;
    }

    public SocialActionEntity findById(UUID id) {
        SocialActionEntity obj = socialActionRepository.findById(id);
        // lançar exceções
        return obj;
    }

    public SocialActionEntity update(SocialActionUpdateDto request){
        SocialActionEntity obj = socialActionRepository.findById(request.id());
        if (request.name() != null){
            obj.setName(request.name());
        }
        if (request.description() != null){
            obj.setDescription(request.description());
        }
        SocialActionEntity updatedObj = socialActionRepository.update(obj);
        return updatedObj;
    }

//    public void delete(UUID id) {
//        socialActionRepository.delete(id);
//    }

}

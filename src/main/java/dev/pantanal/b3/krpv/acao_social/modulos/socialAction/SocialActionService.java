package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionPredicates;
import org.springframework.stereotype.Service;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import java.util.UUID;

@Service
public class SocialActionService {

    @Autowired
    private SocialActionRepository socialActionRepository;
    @Autowired
    private SocialActionPredicates socialActionPredicates;

    public SocialActionEntity create(SocialActionCreateDto dataRequest) {
        SocialActionEntity entity = new SocialActionEntity();
        entity.setName(dataRequest.name());
        entity.setDescription(dataRequest.description());
//        entity.setOrganizer(dataRequest.organizer());
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

        BooleanExpression predicate = socialActionPredicates.buildPredicate(filters);
        Page<SocialActionEntity> objects = socialActionRepository.findAll(pageable, predicate);
        // lançar exceções
        return objects;
    }

    public SocialActionEntity findById(UUID id) {
        SocialActionEntity obj = socialActionRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
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

}

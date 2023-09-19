package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;


import java.util.UUID;

@Service
public class SocialActionService {

    @Autowired
    private SocialActionRepository socialActionRepository;

    public SocialActionEntity create(SocialActionCreateDto dataRequest) {
        SocialActionEntity entity = new SocialActionEntity();
        entity.setName(dataRequest.name());
        entity.setDescription(dataRequest.description());
        SocialActionEntity savedObj = socialActionRepository.save(entity);
        // lançar exceções
        return savedObj;
    }

    public Page<SocialActionEntity> findAll(Pageable pageable, SocialActionParamsDto filters) {

        Page<SocialActionEntity> objects = socialActionRepository.findAll(pageable, filters);
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

    public void delete(UUID id) {
        socialActionRepository.delete(id);
    }

}

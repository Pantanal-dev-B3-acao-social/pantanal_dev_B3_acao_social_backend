package dev.pantanal.b3.krpv.acao_social.service;

import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.dto.response.SocialActionResponseDto;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.repository.socialAction.SocialActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialActionService {

    @Autowired
    private SocialActionRepository socialActionRepository;

    public SocialActionEntity create(SocialActionCreateDto dataRequest) {
        SocialActionEntity entity = new SocialActionEntity();
        entity.setName(dataRequest.name());
        entity.setDescription(dataRequest.description());
        SocialActionEntity savedObj = socialActionRepository.save(entity);
        //
        return savedObj;
    }

}

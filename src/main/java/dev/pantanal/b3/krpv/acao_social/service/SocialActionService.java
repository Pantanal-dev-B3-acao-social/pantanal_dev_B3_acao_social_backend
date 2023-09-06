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

    public SocialActionResponseDto create(SocialActionCreateDto request) {
        SocialActionEntity entity = new SocialActionEntity();
        entity.setName(request.nome());
        entity.setDescription(request.description());
        SocialActionEntity savedObj = socialActionRepository.save(entity);
        SocialActionResponseDto response = new SocialActionResponseDto(
                savedObj.getId(),
                savedObj.getName(),
                savedObj.getDescription()
        );
        return response;
    }

}

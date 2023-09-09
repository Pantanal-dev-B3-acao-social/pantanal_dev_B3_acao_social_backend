package dev.pantanal.b3.krpv.acao_social.service;

import dev.pantanal.b3.krpv.acao_social.dto.SocialActionDto;
import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.repository.socialAction.SocialActionRepository;
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

    public void delete(UUID id) {
        socialActionRepository.delete(id);
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



}

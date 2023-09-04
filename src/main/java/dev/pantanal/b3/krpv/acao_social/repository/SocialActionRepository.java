package dev.pantanal.b3.krpv.acao_social.repository;

import dev.pantanal.b3.krpv.acao_social.config.postgres.PostgresSocialActionRepository;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SocialActionRepository {

    private final PostgresSocialActionRepository postgresSocialActionRepository;

    private SocialActionEntity find(UUID id) {
        return postgresSocialActionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("registro não encontrado: " + id));
    }

    public Page<SocialActionEntity> search(Pageable var1) {
        Page<SocialActionEntity> objsEntities = postgresSocialActionRepository.findAll(var1);
        return objsEntities;
    }

    public SocialActionEntity search(SocialActionEntity obj) {
        SocialActionEntity entitySaved = postgresSocialActionRepository.save(obj);
        return entitySaved;
    }

    public SocialActionEntity update(SocialActionEntity obj) {
        SocialActionEntity entitySaved = find(obj.getId());
        if(obj.getName() != null) {
            entitySaved.setName(obj.getName());
        }
        if(obj.getDescription() != null) {
            entitySaved.setDescription(obj.getDescription());
        }
        SocialActionEntity entityUpdated = postgresSocialActionRepository.save(entitySaved);
        return entityUpdated;
    }

    public void delete(UUID id) {
        SocialActionEntity objEntity = find(id);
        postgresSocialActionRepository.delete(objEntity);
    }

    // TODO: passar dto de filtro
//    public List<SocialActionEntity> searchSocialActionsByName(String name) {
//        return postgresSocialActionRepository.search(name);
//    }

//    public Page<SocialActionEntity> searchSocialActionsByCustomCriteria(String customCriteria, Pageable pageable) {
//        return postgresSocialActionRepository.findByCustomCriteria(customCriteria, pageable);
//    }
}

package dev.pantanal.b3.krpv.acao_social.repository.socialAction;

import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
// TODO: verificar se vamos usar jakarta ou javax
import jakarta.persistence.EntityManager;
//import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.criteria.*;

@Component
@RequiredArgsConstructor
public class SocialActionRepository {

    private final PostgresSocialActionRepository postgresSocialActionRepository;
    private final EntityManager entityManager;

    private SocialActionEntity find(UUID id) {
        return postgresSocialActionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("registro não encontrado: " + id));
    }

    public Page<SocialActionEntity> findAll(Pageable pageable, SocialActionParamsDto filters) {
        // filtro
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SocialActionEntity> criteriaQuery = criteriaBuilder.createQuery(SocialActionEntity.class);
        Root<SocialActionEntity> root = criteriaQuery.from(SocialActionEntity.class);
        Predicate[] predicatesArray = SocialActionSpecifications.getPredicates(filters, root, criteriaBuilder);
        criteriaQuery.where(predicatesArray);
        TypedQuery<SocialActionEntity> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<SocialActionEntity> resultList = query.getResultList();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(SocialActionEntity.class)));
        countQuery.where(predicatesArray);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        Page<SocialActionEntity> resultPage = new PageImpl<>(resultList, pageable, totalCount);
        return resultPage;
    }

    public SocialActionEntity save(SocialActionEntity obj) {
        SocialActionEntity socialActionEntity = postgresSocialActionRepository.save(obj);
        return socialActionEntity;
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

}

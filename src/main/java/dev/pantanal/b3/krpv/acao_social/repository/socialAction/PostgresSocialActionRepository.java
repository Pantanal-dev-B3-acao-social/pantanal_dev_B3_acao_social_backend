package dev.pantanal.b3.krpv.acao_social.repository.socialAction;

import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@Repository
public interface PostgresSocialActionRepository extends JpaRepository<SocialActionEntity, UUID> {

//    Page<SocialActionEntity> findAll(Pageable pageable, SocialActionParamsDto filters);
    Page<SocialActionEntity> findAll(Specification<SocialActionEntity> spec, Pageable pageable);

    default Page<SocialActionEntity> findAllWithFilters(Pageable pageable, SocialActionParamsDto filters) {
        Specification<SocialActionEntity> spec = SocialActionSpecifications.byFilters(filters);
        return findAll(spec, pageable);
    }
}

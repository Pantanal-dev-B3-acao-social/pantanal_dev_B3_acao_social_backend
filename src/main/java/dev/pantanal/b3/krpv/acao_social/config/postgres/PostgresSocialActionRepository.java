package dev.pantanal.b3.krpv.acao_social.config.postgres;

import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostgresSocialActionRepository extends JpaRepository<SocialActionEntity, UUID> {
//    List<SocialActionEntity> search(String name);

    //    @Query("SELECT sa FROM SocialActionEntity sa WHERE sa.customField = :customCriteria")
//    Page<SocialActionEntity> findByCustomCriteria(String customCriteria, Pageable pageable);


}

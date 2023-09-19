package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SocialActionSpecifications {

    public static Specification<SocialActionEntity> byFilters(SocialActionParamsDto filters) {
        return (Root<SocialActionEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate[] predicates = getPredicates(filters, root, criteriaBuilder);
            return criteriaBuilder.and(predicates);
        };
    }

    public static Predicate[] getPredicates(SocialActionParamsDto filters, Root<SocialActionEntity> root, CriteriaBuilder criteriaBuilder) {
        // Crie predicados com base nos filtros em SocialActionParamsDto
        List<Predicate> predicates = new ArrayList<>();
        if (filters.name() != null) {
            predicates.add(criteriaBuilder.equal(root.get("name"), filters.name()));
        }
        if (filters.description() != null) {
            predicates.add(criteriaBuilder.equal(root.get("description"), filters.description()));
        }
        return predicates.toArray(new Predicate[0]);
    }
}

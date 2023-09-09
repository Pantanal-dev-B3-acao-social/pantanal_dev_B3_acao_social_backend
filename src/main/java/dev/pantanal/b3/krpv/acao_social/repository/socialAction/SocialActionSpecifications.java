package dev.pantanal.b3.krpv.acao_social.repository.socialAction;
import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
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
        // Exemplo:
        List<Predicate> predicates = new ArrayList<>();

        if (filters.nome() != null) {
            predicates.add(criteriaBuilder.equal(root.get("nome"), filters.nome()));
        }

        if (filters.description() != null) {
            predicates.add(criteriaBuilder.equal(root.get("description"), filters.description()));
        }

        // Adicione mais predicados conforme necessário

        return predicates.toArray(new Predicate[0]);
    }
}

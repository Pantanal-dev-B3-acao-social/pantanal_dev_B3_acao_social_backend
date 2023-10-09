package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.QOngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import org.springframework.stereotype.Component;

@Component
public class OngPredicates {

    public BooleanExpression buildPredicate(OngParamsDto filters) {
        QOngEntity qEntity = QOngEntity.ongEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();
        if (filters.name() != null) {
            StringPath filterPath = qEntity.name;
            predicate = predicate.and(filterPath.eq(filters.name()));
        }
        if (filters.cnpj() != null) {
            StringPath filterPath = qEntity.cnpj;
            predicate = predicate.and(filterPath.eq(filters.cnpj()));
        }
        if (filters.status() != null) {
            EnumPath<StatusEnum> filterPath = qEntity.status;
            predicate = predicate.and(filterPath.eq(filters.status()));
        }
        return predicate;
    }
}

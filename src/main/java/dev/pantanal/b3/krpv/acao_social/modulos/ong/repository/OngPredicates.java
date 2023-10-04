package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.QOngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import org.springframework.stereotype.Component;

@Component
public class OngPredicates {

    public static BooleanExpression buildPredicate(OngParamsDto filters){

        QOngEntity qOngEntity = QOngEntity.ongEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null) {
            StringPath filterNamePath =  qOngEntity.name;
            predicate = predicate.and(filterNamePath.like(filters.name()+ "%"));
        }
        if (filters.cnpj() != null) {
            StringPath filterCnpjPath = qOngEntity.cnpj;
            predicate = predicate.and(filterCnpjPath.eq(filters.cnpj()));
        }
        return predicate;
    }
}

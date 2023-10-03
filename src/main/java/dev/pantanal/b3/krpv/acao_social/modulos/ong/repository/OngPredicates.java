package dev.pantanal.b3.krpv.acao_social.modulos.ong.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.QOngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import org.springframework.stereotype.Component;

public class OngPredicates {

    public BooleanExpression buildPredicate(OngParamsDto filters){

        QOngEntity qOngEntity = QOngEntity.ongEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null) {
            StringPath filterPath =  qOngEntity.name;
            predicate = predicate.and(filterPath.like(filters.name()+ "%"));
        }
        if (filters.cnpj() != null) {
            StringPath filterPath = qOngEntity.cnpj;
            predicate = predicate.and(filterPath.eq(filters.cnpj()));
        }
        return predicate;
    }
}

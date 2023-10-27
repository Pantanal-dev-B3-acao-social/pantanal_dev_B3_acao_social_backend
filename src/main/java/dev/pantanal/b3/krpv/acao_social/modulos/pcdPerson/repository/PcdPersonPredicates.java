package dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.QPcdPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request.PcdPersonParamsDto;
import org.springframework.stereotype.Component;

@Component
public class PcdPersonPredicates {

    public BooleanExpression buildPredicate(PcdPersonParamsDto filters){

        QPcdPersonEntity qPcdPersonEntity = QPcdPersonEntity.pcdPersonEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.personId() != null) {
            predicate = predicate.and(qPcdPersonEntity.person.id.in(filters.personId()));
        }
        if (filters.pcdId() != null) {
            predicate = predicate.and(qPcdPersonEntity.pcd.id.in(filters.pcdId()));
        }
        return predicate;
    }

}
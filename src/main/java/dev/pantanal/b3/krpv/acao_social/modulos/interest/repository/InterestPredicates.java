package dev.pantanal.b3.krpv.acao_social.modulos.interest.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.QInterestEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.request.InterestParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.QPersonEntity;
import org.springframework.stereotype.Component;

@Component
public class InterestPredicates {

    public BooleanExpression buildPredicate(InterestParamsDto filters){

        QInterestEntity qInterestEntity = QInterestEntity.interestEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.person() != null) {
            predicate = predicate.and(qInterestEntity.person.id.in(filters.person()));
        }
        if (filters.category() != null) {
            predicate = predicate.and(qInterestEntity.person.id.in(filters.category()));
        }

        return predicate;
    }

}
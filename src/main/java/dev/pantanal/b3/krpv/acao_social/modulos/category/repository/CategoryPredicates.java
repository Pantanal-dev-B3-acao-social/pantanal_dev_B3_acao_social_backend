package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.category.QCategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request.CategoryParamsDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryPredicates {

    public BooleanExpression buildPredicate(CategoryParamsDto filters){

        QCategoryEntity qSocialActionEntity = QCategoryEntity.categoryEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null) {
            StringPath filterPath = qSocialActionEntity.name;
            predicate = predicate.and(filterPath.like(filters.name()+ "%"));
        }
        if (filters.description() != null) {
            StringPath filterPath = qSocialActionEntity.description;
            predicate = predicate.and(filterPath.eq(filters.description()));
        }
        if (filters.code() != null) {
            StringPath filterPath = qSocialActionEntity.code;
            predicate = predicate.and(filterPath.eq(filters.code()));
        }
        return predicate;
    }

}

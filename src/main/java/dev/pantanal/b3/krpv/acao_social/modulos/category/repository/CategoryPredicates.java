package dev.pantanal.b3.krpv.acao_social.modulos.category.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.QCategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request.CategoryParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.QCategoryGroupEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryPredicates {

    public BooleanExpression buildPredicate(CategoryParamsDto filters){

        QCategoryEntity qEntity = QCategoryEntity.categoryEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null) {
            StringPath filterPath = qEntity.name;
            predicate = predicate.and(filterPath.like(filters.name()+ "%"));
        }
        if (filters.description() != null) {
            StringPath filterPath = qEntity.description;
            predicate = predicate.and(filterPath.eq(filters.description()));
        }
        if (filters.code() != null) {
            StringPath filterPath = qEntity.code;
            predicate = predicate.and(filterPath.eq(filters.code()));
        }
        if (filters.categoryGroup() != null) {
            QCategoryGroupEntity filterPath = qEntity.categoryGroup;
            predicate = predicate.and(filterPath.eq(filters.categoryGroup()));
        }
        return predicate;
    }

}

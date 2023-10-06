package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SocialActionPredicates {

    public BooleanExpression buildPredicate(SocialActionParamsDto filters){

        QSocialActionEntity qSocialActionEntity = QSocialActionEntity.socialActionEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null){
            StringPath filterPath = qSocialActionEntity.name;
            predicate = predicate.and(filterPath.like(filters.name() + "%"));
        }
        if (filters.description() != null){
            StringPath filterPath = qSocialActionEntity.description;
            predicate = predicate.and(filterPath.eq(filters.description()));
        }
//        if (filters.categorySocialActionTypeEntities() != null){
//            ComparablePath<List<CategorySocialActionTypeEntity>> filterPath = qSocialActionEntity.categorySocialActionTypeEntities;
//            predicate = predicate.and(filterPath.eq(filters.categorySocialActionTypeEntities()));
//        }
        return predicate;
    }

}

package dev.pantanal.b3.krpv.acao_social.modulos.pcd.repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.QPcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdParamsDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PcdPredicates {

    public BooleanExpression buildPredicate(PcdParamsDto filters){

        QPcdEntity qPcdEntity = QPcdEntity.pcdEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null){
            StringPath filterPath = qPcdEntity.name;
            predicate = predicate.and(filterPath.like(filters.name() + "%"));
        }
        if (filters.observation() != null){
            StringPath filterPath = qPcdEntity.observation;
            predicate = predicate.and(filterPath.eq(filters.observation()));
        }
        if (filters.code() != null){
            StringPath filterPath = qPcdEntity.code;
            predicate = predicate.and(filterPath.eq(filters.code()));
        }
        if (filters.acronym() != null){
            StringPath filterPath = qPcdEntity.acronym;
            predicate = predicate.and(filterPath.eq(filters.acronym()));
        }
        return predicate;
    }

}

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import org.springframework.stereotype.Component;

@Component
public class SocialActionPredicates {

    public BooleanExpression buildPredicate(SocialActionParamsDto filters){

        QSocialActionEntity qSocialActionEntity = QSocialActionEntity.socialActionEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null){
            StringPath filterNamePath = qSocialActionEntity.name;
            predicate = predicate.and(filterNamePath.like(filters.name()+ "%"));
        }
        if (filters.description() != null){
            StringPath filterDescriptionPath = qSocialActionEntity.description;
            predicate = predicate.and(filterDescriptionPath.eq(filters.description()));
        }
        if (filters.organizer() != null){
            StringPath filterOrganizerPath = qSocialActionEntity.organizer;
            predicate = predicate.and(filterOrganizerPath.eq(filters.organizer()));
        }
        return predicate;
    }

}

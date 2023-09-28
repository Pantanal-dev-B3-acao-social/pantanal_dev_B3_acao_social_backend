package dev.pantanal.b3.krpv.acao_social.modulos.session.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.session.QSessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request.SessionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SessionPredicates {

    public BooleanExpression buildPredicate(SessionParamsDto filters){

        QSessionEntity qSocialActionEntity = QSessionEntity.sessionEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.description() != null) {
            StringPath filterPath = qSocialActionEntity.description;
            predicate = predicate.and(filterPath.eq(filters.description()));
        }
        if (filters.time() != null) {
            // TODO assim que implementa filter do LocalDateTime?
            DateTimePath<LocalDateTime> filterPath = qSocialActionEntity.time;
            predicate = predicate.and(filterPath.eq(filters.time()));
        }
        if (filters.status() != null) {
            EnumPath<StatusEnum> filterPath = qSocialActionEntity.status;
            predicate = predicate.and(filterPath.eq(filters.status()));
        }
        if (filters.visibility() != null) {
            EnumPath<VisibilityEnum> filterPath = qSocialActionEntity.visibility;
            predicate = predicate.and(filterPath.eq(filters.visibility()));
        }
        if (filters.socialAction() != null) {
            QSocialActionEntity filterPath = qSocialActionEntity.socialAction;
            predicate = predicate.and(filterPath.eq(filters.socialAction()));
        }
// TODO: implementar outros atributos
        return predicate;
    }
}

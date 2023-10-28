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
        if (filters.dateStartTime() != null) {
            DateTimePath<LocalDateTime> filterPath = qSocialActionEntity.dateStartTime;
            predicate = predicate.and(filterPath.eq(filters.dateStartTime()));
        }
        if (filters.dateEndTime() != null) {
            DateTimePath<LocalDateTime> filterPath = qSocialActionEntity.dateEndTime;
            predicate = predicate.and(filterPath.eq(filters.dateEndTime()));
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
        if (filters.engagementScore() != null) {
            NumberPath<Integer> filterPath = qSocialActionEntity.engagementScore;
            predicate = predicate.and(filterPath.eq(filters.engagementScore()));
        }
// TODO: implementar outros atributos
        return predicate;
    }
}

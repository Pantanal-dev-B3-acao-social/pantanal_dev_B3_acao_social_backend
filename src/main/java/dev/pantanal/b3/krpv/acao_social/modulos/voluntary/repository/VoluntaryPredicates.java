package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.person.QPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.QVoluntaryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request.VoluntaryParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class VoluntaryPredicates {

    public BooleanExpression buildPredicate(VoluntaryParamsDto filters){

        QVoluntaryEntity qEntity = QVoluntaryEntity.voluntaryEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.observation() != null) {
            StringPath filterPath = qEntity.observation;
            predicate = predicate.and(filterPath.eq(filters.observation()));
        }
        if (filters.approvedBy() != null) {
            QPersonEntity filterPath = qEntity.approvedBy;
            predicate = predicate.and(filterPath.eq(filters.approvedBy()));
        }
        if (filters.approvedDate() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.approvedDate;
            predicate = predicate.and(filterPath.eq(filters.approvedDate()));
        }
        if (filters.status() != null) {
            EnumPath<StatusEnum> filterPath = qEntity.status;
            predicate = predicate.and(filterPath.eq(filters.status()));
        }
        if (filters.socialAction() != null) {
            QSocialActionEntity filterPath = qEntity.socialAction;
            predicate = predicate.and(filterPath.eq(filters.socialAction()));
        }
        if (filters.person() != null) {
            QPersonEntity filterPath = qEntity.person;
            predicate = predicate.and(filterPath.eq(filters.person()));
        }
        if (filters.feedbackVoluntary() != null) {
            StringPath filterPath = qEntity.feedbackVoluntary;
            predicate = predicate.and(filterPath.eq(filters.feedbackVoluntary()));
        }
        if (filters.feedbackScoreVoluntary() != null) {
            NumberPath filterPath = qEntity.feedbackScoreVoluntary;
            predicate = predicate.and(filterPath.eq(filters.feedbackScoreVoluntary()));
        }
        return predicate;
    }
}

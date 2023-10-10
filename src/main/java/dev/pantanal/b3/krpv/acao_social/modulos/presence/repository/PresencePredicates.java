package dev.pantanal.b3.krpv.acao_social.modulos.presence.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.person.QPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.QPresenceEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.request.PresenceParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.QSessionEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class PresencePredicates {

    public BooleanExpression buildPredicate(PresenceParamsDto filters){

        QPresenceEntity qEntity = QPresenceEntity.presenceEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.approvedBy() != null) {
            QPersonEntity filterPath = qEntity.approvedBy;
            predicate = predicate.and(filterPath.eq(filters.approvedBy()));
        }
        if (filters.approvedDate() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.approvedDate;
            predicate = predicate.and(filterPath.eq(filters.approvedDate()));
        }
        if (filters.session() != null) {
            QSessionEntity filterPath = qEntity.session;
            predicate = predicate.and(filterPath.eq(filters.session()));
        }
        if (filters.person() != null) {
            QPersonEntity filterPath = qEntity.person;
            predicate = predicate.and(filterPath.eq(filters.person()));
        }
        return predicate;
    }
}

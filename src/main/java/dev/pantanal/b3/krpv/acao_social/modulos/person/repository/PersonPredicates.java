package dev.pantanal.b3.krpv.acao_social.modulos.person.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.person.QPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PersonPredicates {

    public BooleanExpression buildPredicate(PersonParamsDto filters){

        QPersonEntity qEntity = QPersonEntity.personEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null) {
            StringPath filterPath = qEntity.name;
            predicate = predicate.and(filterPath.eq(filters.name()));
        }
        if (filters.dateBirth() != null) {
            DateTimePath<LocalDateTime> filterPath = qEntity.dateBirth;
            predicate = predicate.and(filterPath.eq(filters.dateBirth()));
        }
        if (filters.status() != null) {
            EnumPath<StatusEnum> filterPath = qEntity.status;
            predicate = predicate.and(filterPath.eq(filters.status()));
        }
        if (filters.cpf() != null) {
            StringPath filterPath = qEntity.cpf;
            predicate = predicate.and(filterPath.eq(filters.cpf()));
        }
// TODO: implementar outros atributos
        return predicate;
    }
}

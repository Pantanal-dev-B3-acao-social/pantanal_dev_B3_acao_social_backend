package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.QContractEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.enums.StatusEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContractPredicates {

    public BooleanExpression buildPredicate(ContractParamsDto filters){

        QContractEntity qContractEntity = QContractEntity.contractEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.companyId() != null) {
            predicate = predicate.and(qContractEntity.company.id.in(filters.companyId()));
        }
        if (filters.socialActionId() != null) {
            predicate = predicate.and(qContractEntity.socialAction.id.in(filters.socialActionId()));
        }
        if (filters.ongId() != null) {
            predicate = predicate.and(qContractEntity.ong.id.in(filters.ongId()));
        }
        if (filters.processId() != null){
            predicate = predicate.and(qContractEntity.processId.in(filters.processId()));
        }
        if (filters.title() != null){
            StringPath filterPath = qContractEntity.title;
            predicate = predicate.and(filterPath.eq(filters.title()));
        }
        if (filters.description() != null){
            StringPath filterPath = qContractEntity.description;
            predicate = predicate.and(filterPath.eq(filters.description()));
        }
        if (filters.justification() != null){
            StringPath filterPath = qContractEntity.justification;
            predicate = predicate.and(filterPath.eq(filters.justification()));
        }
        if (filters.status() != null){
            EnumPath<StatusEnum> enumPath = qContractEntity.status;
            predicate = predicate.and(enumPath.eq(filters.status()));
        }
        if (filters.evaluatedAt() != null){
            DateTimePath<LocalDateTime> datePath = qContractEntity.evaluatedAt;
            predicate = predicate.and(datePath.eq(filters.evaluatedAt()));
        }
        if (filters.evaluetedBy() != null){
            predicate = predicate.and(qContractEntity.evaluatedBy.id.in(filters.evaluetedBy()));
        }

        return predicate;
    }

}
package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.QContractEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractParamsDto;
import org.springframework.stereotype.Component;

@Component
public class ContractPredicates {

    public BooleanExpression buildPredicate(ContractParamsDto filters){

        QContractEntity qContractEntity = QContractEntity.contractEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

//        if (filters.person() != null) {
//            predicate = predicate.and(qContractEntity.person.id.in(filters.person()));
//        }
//        if (filters.category() != null) {
//            predicate = predicate.and(qContractEntity.person.id.in(filters.category()));
//        }

        return predicate;
    }

}
package dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.QInvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.QCompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InvestmentPredicates {

    public BooleanExpression buildPredicate(InvestmentParamsDto filters){

        QInvestmentEntity qInvestmentEntity = QInvestmentEntity.investmentEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.value_money() != null) {
            NumberPath<Double> filterPath =  qInvestmentEntity.value_money;
            predicate = predicate.and(filterPath.like(filters.value_money()+ "%"));
        }
        if (filters.date() != null) {
            DateTimePath<LocalDateTime> filterPath =  qInvestmentEntity.date;
            predicate = predicate.and(filterPath.eq(filters.date()));
        }
        if (filters.motivation() != null) {
            StringPath filterPath = qInvestmentEntity.motivation;
            predicate = predicate.and(filterPath.eq(filters.motivation()));
        }
        if (filters.approvedAt() != null) {
            DateTimePath<LocalDateTime> filterPath = qInvestmentEntity.approvedAt;
            predicate = predicate.and(filterPath.eq(filters.approvedAt()));
        }
        if (filters.socialAction() != null){
            QSocialActionEntity filterPath = qInvestmentEntity.socialAction;
            predicate = predicate.and(filterPath.eq(filters.socialAction()));
        }
        if (filters.company() != null){
            QCompanyEntity filterPath = qInvestmentEntity.company;
            predicate = predicate.and(filterPath.eq(filters.company()));
        }
        return predicate;
    }

}
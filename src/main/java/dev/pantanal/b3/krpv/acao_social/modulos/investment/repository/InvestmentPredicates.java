package dev.pantanal.b3.krpv.acao_social.modulos.investment.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.QInvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request.InvestmentParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.QCompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.QPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class InvestmentPredicates {

    public BooleanExpression buildPredicate(InvestmentParamsDto filters){

        QInvestmentEntity qInvestmentEntity = QInvestmentEntity.investmentEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.valueMoney() != null) {
            NumberPath<BigDecimal> filterPath =  qInvestmentEntity.valueMoney;
            predicate = predicate.and(filterPath.like(filters.valueMoney()+ "%"));
        }
        if (filters.date() != null) {
            DateTimePath<LocalDateTime> filterPath =  qInvestmentEntity.date;
            predicate = predicate.and(filterPath.eq(filters.date()));
        }
        if (filters.motivation() != null) {
            StringPath filterPath = qInvestmentEntity.motivation;
            predicate = predicate.and(filterPath.eq(filters.motivation()));
        }
        if (filters.approvedDate() != null) {
            DateTimePath<LocalDateTime> filterPath = qInvestmentEntity.approvedDate;
            predicate = predicate.and(filterPath.eq(filters.approvedDate()));
        }
        if (filters.approvedBy() != null) {
            QPersonEntity filterPath = qInvestmentEntity.approvedBy;
            predicate = predicate.and(filterPath.eq(filters.approvedBy()));
        }
        if (filters.socialAction() != null) {
            QSocialActionEntity filterPath = qInvestmentEntity.socialAction;
            predicate = predicate.and(filterPath.eq(filters.socialAction()));
        }
        if (filters.company() != null) {
            QCompanyEntity filterPath = qInvestmentEntity.company;
            predicate = predicate.and(filterPath.eq(filters.company()));
        }
        return predicate;
    }

}
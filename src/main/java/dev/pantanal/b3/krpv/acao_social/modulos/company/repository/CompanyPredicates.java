package dev.pantanal.b3.krpv.acao_social.modulos.company.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import dev.pantanal.b3.krpv.acao_social.modulos.company.QCompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request.CompanyParamsDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyPredicates {

    public BooleanExpression buildPredicate(CompanyParamsDto filters){

        QCompanyEntity qCompanyEntity = QCompanyEntity.companyEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null) {
            StringPath filterPath =  qCompanyEntity.name;
            predicate = predicate.and(filterPath.like(filters.name()+ "%"));
        }
        if (filters.description() != null) {
            StringPath filterPath =  qCompanyEntity.description;
            predicate = predicate.and(filterPath.eq(filters.description()));
        }
        if (filters.cnpj() != null) {
            StringPath filterPath = qCompanyEntity.cnpj;
            predicate = predicate.and(filterPath.eq(filters.cnpj()));
        }
        return predicate;
    }

}
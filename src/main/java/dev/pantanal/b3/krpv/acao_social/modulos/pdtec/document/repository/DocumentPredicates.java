package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.QDocumentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentParamsDto;
import org.springframework.stereotype.Component;


@Component
public class DocumentPredicates {

    public BooleanExpression buildPredicate(DocumentParamsDto filters){

        QDocumentEntity qDocumentEntity = QDocumentEntity.documentEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.contractId() != null) {
            predicate = predicate.and(qDocumentEntity.contract.id.in(filters.contractId()));
        }
        if (filters.documentPdtecId() != null) {
            predicate = predicate.and(qDocumentEntity.pdtecDocumentId.in(filters.documentPdtecId()));
        }
        return predicate;
    }

}
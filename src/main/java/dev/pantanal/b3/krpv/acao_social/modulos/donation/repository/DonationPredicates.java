package dev.pantanal.b3.krpv.acao_social.modulos.donation.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.QDonationEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.QPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.QSocialActionEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DonationPredicates {

    public BooleanExpression buildPredicate(DonationParamsDto filters) {
        QDonationEntity qEntity = QDonationEntity.donationEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();
//        if (filters.socialActionEntity() != null) {
//            QSocialActionEntity filterPath = qEntity.socialActionEntity;
//            predicate = predicate.and(filterPath.eq(filters.socialActionEntity()));
//        }
//        if (filters.donatedByEntity() != null) {
//            QPersonEntity filterPath = qEntity.donatedByEntity;
//            predicate = predicate.and(filterPath.eq(filters.donatedByEntity()));
//        }
//        if (filters.donationDate() != null) {
//            DateTimePath<LocalDateTime> filterPath = qEntity.donationDate;
//            predicate = predicate.and(filterPath.eq(filters.donationDate()));
//        }
//        if (filters.valueMoney() != null) {
//            NumberPath<BigDecimal> filterPath = qEntity.valueMoney;
//            predicate = predicate.and(filterPath.eq(filters.valueMoney()));
//        }
        if (filters.motivation() != null) {
            StringPath filterPath = qEntity.motivation;
            predicate = predicate.and(filterPath.eq(filters.motivation()));
        }
//        if (filters.approvedBy() != null) {
//            QPersonEntity filterPath = qEntity.approvedBy;
//            predicate = predicate.and(filterPath.eq(filters.approvedBy()));
//        }
//        if (filters.approvedDate() != null) {
//            DateTimePath<LocalDateTime> filterPath = qEntity.approvedDate;
//            predicate = predicate.and(filterPath.eq(filters.approvedDate()));
//        }
//        if (filters.lastModifiedBy() != null) {
//            ComparablePath<UUID> filterPath = qEntity.lastModifiedBy;
//            predicate = predicate.and(filterPath.eq(filters.lastModifiedBy()));
//        }
//        if (filters.createdDate() != null) {
//            DateTimePath<LocalDateTime> filterPath = qEntity.createdDate;
//            predicate = predicate.and(filterPath.eq(filters.createdDate()));
//        }
//        if (filters.lastModifiedDate() != null) {
//            DateTimePath<LocalDateTime> filterPath = qEntity.lastModifiedDate;
//            predicate = predicate.and(filterPath.eq(filters.lastModifiedDate()));
//        }
//        if (filters.deletedDate() != null) {
//            DateTimePath<LocalDateTime> filterPath = qEntity.deletedDate;
//            predicate = predicate.and(filterPath.eq(filters.deletedDate()));
//        }
//        if (filters.deletedBy() != null) {
//            ComparablePath<UUID> filterPath = qEntity.deletedBy;
//            predicate = predicate.and(filterPath.eq(filters.deletedBy()));
//        }
        return predicate;
    }

}

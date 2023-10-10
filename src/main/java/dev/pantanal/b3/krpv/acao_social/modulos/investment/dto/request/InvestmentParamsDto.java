package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InvestmentParamsDto(
        BigDecimal valueMoney,

        //@NotBlank(message = "field date can not be empty")
        LocalDateTime date,

        String motivation,

        PersonEntity approvedBy,
        LocalDateTime approvedDate,
        SocialActionEntity socialAction,
        CompanyEntity company,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;

import java.time.LocalDateTime;

public record InvestmentParamsDto(
        Double value_money,
        LocalDateTime date,
        String motivation,
        LocalDateTime approvedAt,

        SocialActionEntity socialAction,

        CompanyEntity company
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request;

import java.time.LocalDateTime;

public record InvestmentParamsDto(
        Double value_money,
        LocalDateTime date,
        String motivation,
        LocalDateTime approvedAt
) {}

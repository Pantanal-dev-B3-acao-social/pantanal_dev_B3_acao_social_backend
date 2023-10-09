package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request;

import java.time.LocalDateTime;

public record InvestmentUpdateDto(
        //@NotBlank(message = "field value_money can not be empty")
        Double value_money,

        //@NotBlank(message = "field date can not be empty")
        LocalDateTime date,

        String motivation,

        //@NotBlank(message = "field approvedAt can not be empty")
        LocalDateTime approvedAt
) {
}

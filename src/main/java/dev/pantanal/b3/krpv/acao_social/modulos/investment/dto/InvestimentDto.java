package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvestimentDto(
        @Valid
        @NotBlank(message = "field value_money can not be empty")
        BigDecimal valueMoney,

        @NotBlank(message = "field value_money can not be empty")
        LocalDateTime date,

        @NotBlank(message = "field value_money can not be empty")
        String motivation,

        @NotBlank(message = "field value_money can not be empty")
        LocalDateTime approvedAt

) {
}

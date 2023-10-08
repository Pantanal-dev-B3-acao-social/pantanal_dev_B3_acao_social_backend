package dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record InvestimentDto(
        @Valid
        @NotBlank(message = "field value_money can not be empty")
        long value_money,

        @NotBlank(message = "field value_money can not be empty")
        LocalDateTime date,

        @NotBlank(message = "field value_money can not be empty")
        String motivation,

        @NotBlank(message = "field value_money can not be empty")
        LocalDateTime approvedAt

) {
}

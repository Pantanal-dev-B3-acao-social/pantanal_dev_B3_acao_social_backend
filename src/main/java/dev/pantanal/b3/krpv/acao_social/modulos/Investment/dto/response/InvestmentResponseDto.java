package dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.response;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public record InvestmentResponseDto(

        @Valid
        @NotBlank(message = "field id can not be empty")
        UUID id,

        @NotBlank(message = "field value_money can not be empty")
        Double value_money,

        @NotBlank(message = "field date can not be empty")
        LocalDateTime date,

        @NotBlank(message = "field motivation can not be empty")
        String motivation,

        @NotBlank(message = "field approvedAt can not be empty")
        LocalDateTime approvedAt

) {
}

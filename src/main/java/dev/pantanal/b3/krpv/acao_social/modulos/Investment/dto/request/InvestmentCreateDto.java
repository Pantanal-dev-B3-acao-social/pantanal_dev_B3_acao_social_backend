package dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InvestmentCreateDto(

        @Valid
        //@NotBlank(message = "field value_money can not be empty")
        Double value_money,

        //@NotBlank(message = "field date can not be empty")
        LocalDateTime date,

        @NotBlank(message = "field motivation can not be empty")
        String motivation,

        //@NotBlank(message = "field approvedAt can not be empty")
        LocalDateTime approvedAt

) {
}

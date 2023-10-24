package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InvestmentCreateDto(
        @NotNull
        BigDecimal valueMoney,
        @FutureOrPresent
        LocalDateTime date,
        @NotBlank(message = "field motivation can not be empty")
        String motivation,
        @NotNull(message = "field approvedBy can not be empty")
        UUID approvedBy,
        @FutureOrPresent
        LocalDateTime approvedDate,
        @NotNull(message = "field socialAction can not be empty")
        UUID socialAction,
        @NotNull(message = "field company can not be empty")
        UUID company

) {
}

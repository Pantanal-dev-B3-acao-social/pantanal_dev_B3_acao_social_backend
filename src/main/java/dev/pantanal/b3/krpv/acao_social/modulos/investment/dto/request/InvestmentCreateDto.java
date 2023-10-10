package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvestmentCreateDto(
        @NotNull(message = "field valueMoney can not be empty")
        BigDecimal valueMoney,
        @NotNull(message = "field date can not be empty")
        LocalDateTime date,
        @NotBlank(message = "field motivation can not be empty")
        String motivation,
        @NotNull(message = "field approvedBy can not be empty")
        PersonEntity approvedBy,
        @NotNull(message = "field approvedDate can not be empty")
        LocalDateTime approvedDate,
        @NotNull(message = "field socialAction can not be empty")
        SocialActionEntity socialAction,
        @NotNull(message = "field company can not be empty")
        CompanyEntity company

) {
}

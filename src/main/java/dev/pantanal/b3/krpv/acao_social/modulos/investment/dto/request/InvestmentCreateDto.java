package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvestmentCreateDto(
        @NotBlank(message = "field valueMoney can not be empty")
        BigDecimal valueMoney,
        @NotBlank(message = "field date can not be empty")
        LocalDateTime date,
        @NotBlank(message = "field motivation can not be empty")
        String motivation,
        @NotBlank(message = "field approvedBy can not be empty")
        PersonEntity approvedBy,
        @NotBlank(message = "field approvedDate can not be empty")
        LocalDateTime approvedDate,
        @NotBlank(message = "field socialAction can not be empty")
        SocialActionEntity socialAction,
        @NotBlank(message = "field company can not be empty")
        CompanyEntity company

) {
}

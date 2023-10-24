package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InvestmentUpdateDto(
        BigDecimal valueMoney,
        LocalDateTime date,
        String motivation,
        LocalDateTime approvedDate
) {
}

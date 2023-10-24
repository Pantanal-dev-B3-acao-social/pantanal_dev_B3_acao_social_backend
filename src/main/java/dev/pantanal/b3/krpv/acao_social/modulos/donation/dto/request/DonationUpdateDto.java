package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DonationUpdateDto (

        LocalDateTime donationDate,
        BigDecimal valueMoney,
        String motivation,
        LocalDateTime approvedDate
){}

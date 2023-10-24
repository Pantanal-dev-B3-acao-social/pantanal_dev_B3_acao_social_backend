package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DonationCreateDto (
        @Valid
        @NotNull(message="Campo 'socialActionEntity' não pode estar vazio")
        UUID socialActionEntity,
        @NotNull(message="Campo 'donatedByEntity' não pode estar vazio")
        UUID donatedByEntity,
        @PastOrPresent
        LocalDateTime donationDate,
        @NotNull(message= "Campo 'value_money' não pode estar vazio")
        BigDecimal valueMoney,
        @NotBlank(message= "Campo 'motivation' não pode estar vazio")
        String motivation,
        @NotNull(message= "Campo 'approvedBy' não pode estar vazio")
        UUID approvedBy,
        LocalDateTime approvedDate
){}

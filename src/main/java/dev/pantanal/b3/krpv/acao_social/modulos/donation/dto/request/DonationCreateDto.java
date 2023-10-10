package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonationCreateDto (
        @NotNull(message="Campo 'socialActionEntity' não pode estar vazio")
        SocialActionEntity socialActionEntity,
        @NotNull(message="Campo 'donatedByEntity' não pode estar vazio")
        PersonEntity donatedByEntity,
        @NotNull(message="Campo 'donationDate' não pode estar vazio")
        LocalDateTime donationDate,
        @NotNull(message= "Campo 'value_money' não pode estar vazio")
        BigDecimal valueMoney,
        @NotBlank(message= "Campo 'motivation' não pode estar vazio")
        String motivation,
        @NotNull(message= "Campo 'approvedBy' não pode estar vazio")
        PersonEntity approvedBy,
        LocalDateTime approvedDate
){}

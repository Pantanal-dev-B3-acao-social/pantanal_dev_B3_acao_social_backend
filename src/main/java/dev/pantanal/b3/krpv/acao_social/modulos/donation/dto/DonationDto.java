package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DonationDto(

        SocialActionEntity socialActionEntity,
        PersonEntity donatedByEntity,
        LocalDateTime donationDate,
        BigDecimal valueMoney,
        String motivation,
        PersonEntity approvedBy,
        LocalDateTime approvedDate,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy

){}

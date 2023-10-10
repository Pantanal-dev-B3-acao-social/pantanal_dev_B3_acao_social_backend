package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DonationParamsDto (

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

) {}

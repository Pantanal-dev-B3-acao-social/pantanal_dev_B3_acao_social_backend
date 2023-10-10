package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public record DonationResponseDto (

    UUID id,
    @NotBlank(message="Campo 'socialActionEntity' não pode estar vazio")
    SocialActionEntity socialActionEntity,
    @NotBlank(message="Campo 'donatedByEntity' não pode estar vazio")
    PersonEntity donatedByEntity,
    @NotBlank(message="Campo 'donation_date' não pode estar vazio")
    LocalDateTime donationDate,
    @NotBlank(message= "Campo 'value_money' não pode estar vazio")
    BigDecimal valueMoney,
    @NotBlank(message= "Campo 'motivation' não pode estar vazio")
    String motivation,
    @NotBlank(message= "Campo 'approvedBy' não pode estar vazio")
    PersonEntity approvedBy,
    @NotBlank(message= "Campo 'approvedDate' não pode estar vazio")
    LocalDateTime approvedDate,
    @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
    UUID createdBy,
    @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
    LocalDateTime createdDate,
    UUID lastModifiedBy,
    LocalDateTime lastModifiedDate,
    UUID deletedBy,
    LocalDateTime deletedDate

){}

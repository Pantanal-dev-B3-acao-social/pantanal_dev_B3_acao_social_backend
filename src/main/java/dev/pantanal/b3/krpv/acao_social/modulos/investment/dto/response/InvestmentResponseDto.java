package dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.response;


import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public record InvestmentResponseDto(

        @Valid
        @NotBlank(message = "field id can not be empty")
        UUID id,

        @NotBlank(message = "field value_money can not be empty")
        Double value_money,

        @NotBlank(message = "field date can not be empty")
        LocalDateTime date,

        @NotBlank(message = "field motivation can not be empty")
        String motivation,

        @NotBlank(message = "field approvedAt can not be empty")
        LocalDateTime approvedAt,

        SocialActionEntity socialAction,

        CompanyEntity company,

        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,

        UUID lastModifiedBy,

        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,

        LocalDateTime lastModifiedDate,

        LocalDateTime deletedDate,

        UUID deletedBy



) {
}

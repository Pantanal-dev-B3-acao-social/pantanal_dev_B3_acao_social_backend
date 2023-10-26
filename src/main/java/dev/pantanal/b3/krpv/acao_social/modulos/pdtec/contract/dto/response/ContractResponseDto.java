package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ContractResponseDto(

        UUID id,

        CompanyEntity company,

        SocialActionEntity socialAction,

        OngEntity ong,

        UUID processoId,

        String title,

        String description,

        String justification,

        StatusEnum status,

        LocalDateTime evaluatedAt,

        PersonEntity evaluetedBy,

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

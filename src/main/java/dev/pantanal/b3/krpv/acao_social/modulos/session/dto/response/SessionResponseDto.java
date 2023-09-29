package dev.pantanal.b3.krpv.acao_social.modulos.session.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionResponseDto (

        @NotBlank(message= "Campo 'id' não pode estar vazio")
        UUID id,
        String description,
        @NotBlank(message= "Campo 'socialAction' não pode estar vazio")
        SocialActionEntity socialAction,
        LocalDateTime time,
//        @NotBlank(message= "Campo 'status' não pode estar vazio")
        StatusEnum status,
//        @NotBlank(message= "Campo 'visibility' não pode estar vazio")
        VisibilityEnum visibility,
        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
        UUID lastModifiedBy,
        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}
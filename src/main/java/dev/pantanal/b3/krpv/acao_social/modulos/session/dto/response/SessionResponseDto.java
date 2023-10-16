package dev.pantanal.b3.krpv.acao_social.modulos.session.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionResponseDto (

        @NotBlank(message= "Campo 'id' não pode estar vazio")
        UUID id,
        String description,
        @NotNull(message= "Campo 'socialAction' não pode estar vazio")
        UUID socialActionId,
        LocalDateTime dateStartTime,
        LocalDateTime dateEndTime,
        @NotNull(message= "Campo 'status' não pode estar vazio")
        StatusEnum status,
        @NotNull(message= "Campo 'visibility' não pode estar vazio")
        VisibilityEnum visibility,
        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        UUID lastModifiedBy,
        LocalDateTime lastModifiedDate,
        UUID deletedBy,
        LocalDateTime deletedDate
) {}

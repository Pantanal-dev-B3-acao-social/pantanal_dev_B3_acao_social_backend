package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record SocialActionResponseDto(
        UUID id,
        String name,
        String description,
//        String organizer,
        long version,
        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
        UUID lastModifiedBy,
        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

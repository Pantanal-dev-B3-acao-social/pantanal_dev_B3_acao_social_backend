package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record SocialActionResponseDto(
        @NotBlank(message= "Campo 'id' não pode estar vazio")
        UUID id,
        @NotBlank(message= "Campo 'name' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
        UUID lastModifiedBy,
        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy,
        List<UUID> categoryTypeIds,
        List<UUID> categoryLevelIds
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionLevelEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryEntity;
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
        List<SessionEntity> sessionEntities,
        List<VoluntaryEntity> voluntaryEntities,
        List<CategorySocialActionTypeEntity> categoryType,
        List<CategorySocialActionLevelEntity> categoryLevel,
        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
        UUID lastModifiedBy,
        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

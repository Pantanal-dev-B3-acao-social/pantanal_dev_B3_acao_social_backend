package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums.VisibilityCategoryGroupEnum;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryGroupDto(
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'code' não pode estar vazio")
        String code,
        CategoryGroupEntity categoryGroupEntity,
        VisibilityCategoryGroupEnum visibility,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {
}

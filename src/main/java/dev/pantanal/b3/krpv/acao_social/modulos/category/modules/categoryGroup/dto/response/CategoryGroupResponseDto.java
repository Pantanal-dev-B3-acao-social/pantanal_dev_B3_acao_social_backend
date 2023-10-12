package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.category.enums.VisibilityCategoryEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums.VisibilityCategoryGroupEnum;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryGroupResponseDto(
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'code' não pode estar vazio")
        String code,
        UUID parentCategoryGroupId,
        @NotBlank(message= "Campo 'visibility' não pode estar vazio")
        VisibilityCategoryGroupEnum visibility,
        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        UUID lastModifiedBy,
        LocalDateTime lastModifiedDate,
        UUID deletedBy,
        LocalDateTime deletedDate
) { }

package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums.VisibilityCategoryGroupEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryGroupCreateDto(
        @NotBlank(message="Campo 'name' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        CategoryGroupEntity parentCategoryGroupEntity,
        @NotNull(message= "Campo 'visibility' não pode estar vazio")
        VisibilityCategoryGroupEnum visibility
) {
}

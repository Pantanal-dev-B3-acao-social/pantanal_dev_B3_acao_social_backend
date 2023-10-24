package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateDto(
        String name,
        String description
) {
}

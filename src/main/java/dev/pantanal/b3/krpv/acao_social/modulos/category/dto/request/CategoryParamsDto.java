package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CategoryParamsDto (
        String name,
        String description,
        String code,
        CategoryGroupEntity categoryGroup
) {}

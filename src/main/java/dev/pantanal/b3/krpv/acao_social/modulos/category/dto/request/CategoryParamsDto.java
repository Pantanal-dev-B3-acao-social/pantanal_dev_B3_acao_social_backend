package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.enums.VisibilityCategoryEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;

public record CategoryParamsDto (
        String name,
        String description,
        String code,
        CategoryGroupEntity categoryGroup,
        VisibilityCategoryEnum visibility
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums.VisibilityCategoryGroupEnum;
import java.util.UUID;

public record CategoryGroupUpdateDto(
        String name,
        String description,
        VisibilityCategoryGroupEnum visibility,
        UUID parentCategoryGroupId
) {}

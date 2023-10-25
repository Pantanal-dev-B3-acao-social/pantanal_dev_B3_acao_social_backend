package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.enums.VisibilityCategoryEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums.VisibilityCategoryGroupEnum;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryGroupParamsDto(
        String name,
        String description,
        String code,
        UUID parentCategoryGroupId,
        VisibilityCategoryGroupEnum visibility,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

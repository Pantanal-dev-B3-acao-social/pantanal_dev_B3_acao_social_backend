package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.enums.VisibilityCategoryEnum;
import java.util.UUID;

public record CategoryUpdateDto(
        String name,
        String description,
        UUID categoryGroup,
        VisibilityCategoryEnum visibility
) {}

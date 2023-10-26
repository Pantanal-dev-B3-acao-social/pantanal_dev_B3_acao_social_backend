package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.enums.VisibilityCategoryEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CategoryCreateDto (
        @NotBlank(message="Campo 'name' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,

        VisibilityCategoryEnum visibility,
        @NotNull
        UUID categoryGroup
) {}

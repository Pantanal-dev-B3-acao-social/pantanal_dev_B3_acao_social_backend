package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record SocialActionDto (
        @Valid
        @NotBlank(message="Campo 'id' não pode estar vazio")
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotNull
        @NotBlank
        long version,
        List<CategorySocialActionTypeEntity> categorySocialActionTypeEntities

) {}

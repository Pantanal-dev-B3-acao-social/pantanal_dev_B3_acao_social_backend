package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;


public record SocialActionCreateDto(
        @Valid
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        @Size(min = 2)
        String description,
        @NotNull
        UUID ong,
        List<UUID> categoryTypeIds,
        List<UUID> categoryLevelIds

) {}

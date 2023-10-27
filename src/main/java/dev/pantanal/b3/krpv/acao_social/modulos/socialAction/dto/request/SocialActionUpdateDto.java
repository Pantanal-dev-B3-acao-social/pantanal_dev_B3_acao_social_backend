package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;


public record SocialActionUpdateDto(
        String name,
        String description,
        List<UUID> categoryTypeIds,
        List<UUID> categoryLevelIds,
        UUID ong
) {}

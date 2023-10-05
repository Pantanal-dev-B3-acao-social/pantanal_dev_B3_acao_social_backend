package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public record SocialActionParamsDto(
        String name,
        String description,
        List<CategorySocialActionTypeEntity> categorySocialActionTypeEntities

) {}

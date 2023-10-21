package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponseDto(
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'code' não pode estar vazio")
        String code,
        @NotNull
        CategoryGroupEntity categoryGroup,
        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
        UUID lastModifiedBy,
        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) { }

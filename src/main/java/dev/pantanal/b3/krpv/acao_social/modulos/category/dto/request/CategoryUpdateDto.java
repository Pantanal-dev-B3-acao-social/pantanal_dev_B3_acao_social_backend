package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateDto(
        @NotBlank(message="Campo 'name' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'code' não pode estar vazio")
        String code
) {
}

package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.response;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record CategoryResponseDto(
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'code' não pode estar vazio")
        String code,
        Long version
) { }

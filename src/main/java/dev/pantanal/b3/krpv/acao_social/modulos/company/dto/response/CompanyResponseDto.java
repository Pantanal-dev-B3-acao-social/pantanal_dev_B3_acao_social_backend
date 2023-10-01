package dev.pantanal.b3.krpv.acao_social.modulos.company.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CompanyResponseDto (

        UUID id,
        @NotBlank(message="Campo 'name' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'code' não pode estar vazio")
        String cnpj,
        Long version
) {}

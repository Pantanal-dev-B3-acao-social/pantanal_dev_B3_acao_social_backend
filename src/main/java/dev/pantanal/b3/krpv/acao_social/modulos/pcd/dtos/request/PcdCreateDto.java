package dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record PcdCreateDto (

        @NotBlank
        String name,
        @NotBlank
        String observation,
        @NotBlank
        String code,
        @NotBlank
        String acronym
){
}

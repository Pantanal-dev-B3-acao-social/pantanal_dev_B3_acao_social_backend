package dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record PcdParamsDto (


        String name,

        String observation,

        String code,

        String acronym
){
}

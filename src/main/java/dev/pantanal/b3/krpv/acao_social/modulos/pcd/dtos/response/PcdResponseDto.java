package dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.response;

import java.util.UUID;

public record PcdResponseDto (
        UUID id,
        String name,
        String observation,
        String code,
        String acronym
){
}

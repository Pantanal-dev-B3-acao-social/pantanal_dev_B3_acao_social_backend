package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto;

import java.util.UUID;

public record DocumentCreateDto(

        UUID contract,
        UUID documentPdtecId
) {
}

package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request;

import java.util.UUID;

public record DocumentParamsDto(
        UUID contractId,
        UUID documentPdtecId
) {
}

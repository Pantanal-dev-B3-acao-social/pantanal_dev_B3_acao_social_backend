package dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.request;

import java.util.UUID;

public record InterestParamsDto (
        UUID person,
        UUID category
){
}

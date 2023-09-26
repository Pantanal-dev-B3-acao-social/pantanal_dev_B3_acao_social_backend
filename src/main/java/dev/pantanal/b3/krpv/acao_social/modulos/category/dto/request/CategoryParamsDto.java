package dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request;

import jakarta.validation.Valid;

public record CategoryParamsDto (
        @Valid
        String name,
        String description,
        String code
) {}

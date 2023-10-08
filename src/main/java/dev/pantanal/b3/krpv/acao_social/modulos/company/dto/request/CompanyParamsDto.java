package dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request;

import jakarta.validation.Valid;

public record CompanyParamsDto(
        @Valid
        String name,
        String description,
        String cnpj
) { }

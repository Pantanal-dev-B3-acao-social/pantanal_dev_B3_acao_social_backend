package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OngUpdateDto (

        @NotNull
        String name,
        String cnpj,
        UUID managerId
) {}

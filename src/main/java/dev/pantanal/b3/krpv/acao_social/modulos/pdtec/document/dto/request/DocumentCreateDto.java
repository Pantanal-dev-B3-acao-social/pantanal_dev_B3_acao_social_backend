package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.enums.TypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DocumentCreateDto(

        @NotNull
        UUID contractId,
        @NotBlank
        String extension,
        @NotNull
        boolean isPendency,
        @NotBlank
        String name,
        int order,
        @NotNull
        TypeEnum type
) {
}

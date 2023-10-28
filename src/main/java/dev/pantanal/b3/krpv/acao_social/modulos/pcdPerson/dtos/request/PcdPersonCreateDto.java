package dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PcdPersonCreateDto(
        @NotNull
        UUID person,
        @NotNull
        UUID pcd
){
}

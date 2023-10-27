package dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PcdPersonParamsDto(



        UUID personId,

        UUID pcdId
){
}

package dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;

import java.time.LocalDateTime;

public record PersonUpdateDto(
        String name,
        LocalDateTime dateBirth,
        StatusEnum status,
        String cpf
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record PersonCreateDto (
        UUID userId,
        String name,
        LocalDateTime dateBirth,
        StatusEnum status,
        String cpf
) {}

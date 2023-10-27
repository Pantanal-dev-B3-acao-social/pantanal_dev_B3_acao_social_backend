package dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.user.KeycloakUser;

import java.time.LocalDateTime;
import java.util.UUID;

public record PersonCreateDto (
        String name,
        LocalDateTime dateBirth,
        StatusEnum status,
        String cpf,
        KeycloakUser keycloakUser
) {}

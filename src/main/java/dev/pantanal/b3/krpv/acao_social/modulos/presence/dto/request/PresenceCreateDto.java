package dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;
import java.util.UUID;

public record PresenceCreateDto(
        @NotNull
        UUID person,
        @NotNull
        UUID session,
        @NotNull
        UUID approvedBy,
        @PastOrPresent
        LocalDateTime approvedDate
) {}

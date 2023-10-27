package dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record PresenceParamsDto (
        PersonEntity person,
        SessionEntity session,
        PersonEntity approvedBy,
        LocalDateTime approvedDate,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy,
        Integer engagementScore
) {}

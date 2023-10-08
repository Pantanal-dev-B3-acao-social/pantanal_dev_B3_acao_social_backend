package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoluntaryParamsDto (
        String observation,
        SocialActionEntity socialAction,
        PersonEntity person,
        StatusEnum status,
        PersonEntity approvedBy,
        LocalDateTime approvedDate,
        Integer feedbackScoreVoluntary,
        String feedbackVoluntary,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

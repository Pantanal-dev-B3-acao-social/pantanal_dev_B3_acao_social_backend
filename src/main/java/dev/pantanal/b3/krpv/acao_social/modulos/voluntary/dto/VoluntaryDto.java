package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoluntaryDto (
        UUID id,
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

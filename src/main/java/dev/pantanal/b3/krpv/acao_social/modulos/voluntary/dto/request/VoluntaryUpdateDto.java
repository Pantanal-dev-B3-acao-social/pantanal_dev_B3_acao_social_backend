package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoluntaryUpdateDto (
        String observation,
        StatusEnum status,
        PersonEntity approvedBy,
        LocalDateTime approvedDate,
        Integer feedbackScoreVoluntary,
        String feedbackVoluntary
) {}

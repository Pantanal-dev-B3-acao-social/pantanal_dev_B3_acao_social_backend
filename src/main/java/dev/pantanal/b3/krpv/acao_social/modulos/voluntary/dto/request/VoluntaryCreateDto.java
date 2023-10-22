package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoluntaryCreateDto (
        String observation,
        @NotNull
        UUID socialAction,
        @NotNull
        UUID person,
        StatusEnum status,
        @NotNull
        UUID approvedBy,
        @PastOrPresent
        LocalDateTime approvedDate,
        Integer feedbackScoreVoluntary,
        String feedbackVoluntary
) {}

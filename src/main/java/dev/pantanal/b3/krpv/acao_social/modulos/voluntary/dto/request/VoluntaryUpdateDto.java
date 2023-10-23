package dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;
import jakarta.validation.constraints.PastOrPresent;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoluntaryUpdateDto (
        String observation,
        StatusEnum status,
        UUID approvedBy,
        @PastOrPresent
        LocalDateTime approvedDate,
        Integer feedbackScoreVoluntary,
        String feedbackVoluntary
) {}

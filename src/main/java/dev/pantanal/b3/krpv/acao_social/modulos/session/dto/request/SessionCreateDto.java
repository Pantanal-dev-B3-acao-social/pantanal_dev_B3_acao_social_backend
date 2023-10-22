package dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record SessionCreateDto (
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotNull(message= "Campo 'socialAction' não pode estar vazio")
        UUID socialAction,
        @FutureOrPresent
        LocalDateTime dateStartTime,
        @FutureOrPresent
        LocalDateTime dateEndTime,
        StatusEnum status,
        VisibilityEnum visibility
) {}

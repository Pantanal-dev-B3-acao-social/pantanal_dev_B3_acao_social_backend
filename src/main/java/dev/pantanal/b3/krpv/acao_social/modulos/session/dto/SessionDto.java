package dev.pantanal.b3.krpv.acao_social.modulos.session.dto;

import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public record SessionDto(
        UUID id,
        String description,
        SocialActionEntity socialAction,
        LocalDateTime time,
        StatusEnum status,
        VisibilityEnum visibility,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

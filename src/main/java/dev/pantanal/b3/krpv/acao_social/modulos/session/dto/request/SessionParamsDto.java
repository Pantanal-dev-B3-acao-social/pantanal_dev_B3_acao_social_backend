package dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public record SessionParamsDto (
        String description,
        SocialActionEntity socialAction,
        LocalDateTime dateStartTime,
        LocalDateTime dateEndTime,
        StatusEnum status,
        VisibilityEnum visibility,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

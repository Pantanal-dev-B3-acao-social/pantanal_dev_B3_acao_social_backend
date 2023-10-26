package dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import java.time.LocalDateTime;

public record SessionUpdateDto (
        String description,
        LocalDateTime dateStart,
        LocalDateTime dateEnd,
        StatusEnum status,
        VisibilityEnum visibility,
        Integer engagementScore
) {}

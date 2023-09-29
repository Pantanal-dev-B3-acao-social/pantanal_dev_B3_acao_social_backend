package dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.session.enums.VisibilityEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import java.time.LocalDateTime;

public record SessionCreateDto (
//        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
//        @NotBlank(message= "Campo 'socialAction' não pode estar vazio")
        SocialActionEntity socialAction,
        LocalDateTime time,
        StatusEnum status,
//        @NotBlank(message= "Campo 'visibility' não pode estar vazio")
        VisibilityEnum visibility
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public record SocialActionParamsDto(
        String nome,
        String description
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto;

import java.util.UUID;

public record SocialActionDto (
        UUID id,
        String name,
        String description
) {}

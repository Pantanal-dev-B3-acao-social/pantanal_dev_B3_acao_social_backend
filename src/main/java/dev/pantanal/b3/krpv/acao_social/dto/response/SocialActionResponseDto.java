package dev.pantanal.b3.krpv.acao_social.dto.response;

import java.util.UUID;

public record SocialActionResponseDto(
        UUID id,
        String name,
        String description
) {}

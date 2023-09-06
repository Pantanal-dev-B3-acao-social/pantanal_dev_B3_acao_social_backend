package dev.pantanal.b3.krpv.acao_social.dto;

import java.util.UUID;

public record SocialActionDto (
        UUID id,
        String nome,
        String description
) {}

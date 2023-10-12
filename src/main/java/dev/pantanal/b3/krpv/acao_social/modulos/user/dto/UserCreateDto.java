package dev.pantanal.b3.krpv.acao_social.modulos.user.dto;

import java.util.List;

public record UserCreateDto(
        String username,
        boolean enabled,
        String email,
        String firstName,
        String lastName,
        List<CredentialDto> credentials
) {}


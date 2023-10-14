package dev.pantanal.b3.krpv.acao_social.modulos.user.dto;

import java.util.List;
import java.util.Map;

public record UserResponseDto (
        String id,
        String username,
        boolean enabled,
        Boolean totp,
        String email,
        String firstName,
        String lastName,
        Map<String, String> credentials,
        Boolean emailVerified,
        Map<String, List<String>> attributes,
        String self,
        String origin,
        Long createdTimestamp,
        String federationLink,
        String serviceAccountClientId,
        List<String> disableableCredentialTypes
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.user.dto;

record CredentialDto(
        String type,
        String value,
        boolean temporary
) {}
package dev.pantanal.b3.krpv.acao_social.modulos.user.dto;
public record CredentialDto(
        String type,
        String value,
        boolean temporary
) {}
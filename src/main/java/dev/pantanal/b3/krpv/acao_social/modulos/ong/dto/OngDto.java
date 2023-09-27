package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto;

import java.util.UUID;

public record OngDto (

        UUID id,
        String name,
        String cnpj,
        UUID managerId

) {}

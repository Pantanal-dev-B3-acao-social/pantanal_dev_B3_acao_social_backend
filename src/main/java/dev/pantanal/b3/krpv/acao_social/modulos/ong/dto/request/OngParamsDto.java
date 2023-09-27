package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public record OngParamsDto (

        String name,
        String cnpj,
        UUID managerId


) {}

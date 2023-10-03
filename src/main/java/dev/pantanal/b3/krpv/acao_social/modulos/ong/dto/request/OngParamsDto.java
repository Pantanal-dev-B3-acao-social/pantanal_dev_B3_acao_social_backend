package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public record OngParamsDto (
        @Valid
        String name,
        String cnpj
      //  UUID managerId


) {}

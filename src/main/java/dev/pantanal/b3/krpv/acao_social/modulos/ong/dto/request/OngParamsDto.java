package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record OngParamsDto (
        String name,
        StatusEnum status,
        String cnpj,
        UUID createdBy,
        LocalDateTime createdDate,
        UUID lastModifiedBy,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy,
        PersonEntity responsibleEntity
) {}

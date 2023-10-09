package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public record OngDto (
        UUID id,
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

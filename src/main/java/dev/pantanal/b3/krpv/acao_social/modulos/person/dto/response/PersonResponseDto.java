package dev.pantanal.b3.krpv.acao_social.modulos.person.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public record PersonResponseDto (
        UUID id,
        UUID userId,
        String name,
        LocalDateTime dateBirth,
        StatusEnum status,
        String cpf,
        BigInteger engagementScore,
        UUID createdBy,
        LocalDateTime createdDate,
        UUID lastModifiedBy,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
) {}

package dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.response;

import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public record PcdResponseDto (
        UUID id,
        String name,
        String observation,
        String code,
        String acronym,
        UUID CreatedBy,
        UUID LastModifiedBy,
        LocalDateTime CreatedDate,
        LocalDateTime LastModifiedDate,
        LocalDateTime DeletedDate,
        UUID DeletedBy
){
}

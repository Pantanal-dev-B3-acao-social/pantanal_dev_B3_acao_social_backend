package dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.response;

import dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record PcdPersonResponseDto(
        UUID id,
        PersonEntity person,
        PcdEntity pcd,
        UUID CreatedBy,
        UUID LastModifiedBy,
        LocalDateTime CreatedDate,
        LocalDateTime LastModifiedDate,
        LocalDateTime DeletedDate,
        UUID DeletedBy
){
}

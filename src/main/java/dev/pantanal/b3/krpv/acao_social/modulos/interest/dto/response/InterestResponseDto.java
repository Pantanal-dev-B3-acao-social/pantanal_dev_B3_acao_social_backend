package dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record InterestResponseDto (
        UUID id,
        PersonEntity person,
        CategoryEntity category,
        UUID createdBy,
        UUID lastModifiedBy,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy
){
}

package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record OngResponseDto (
//        @NotBlank(message= "Campo 'id' não pode estar vazio")
        UUID id,
//        @NotBlank(message= "Campo 'name' não pode estar vazio")
        String name,
//        @NotBlank(message= "Campo 'status' não pode estar vazio")
        StatusEnum status,
//        @NotBlank(message= "Campo 'cnpj' não pode estar vazio")
        String cnpj,
//        @NotBlank(message= "Campo 'createdBy' não pode estar vazio")
        UUID createdBy,
//        @NotBlank(message= "Campo 'createdDate' não pode estar vazio")
        LocalDateTime createdDate,
        UUID lastModifiedBy,
        LocalDateTime lastModifiedDate,
        LocalDateTime deletedDate,
        UUID deletedBy,
        PersonEntity person
) {}

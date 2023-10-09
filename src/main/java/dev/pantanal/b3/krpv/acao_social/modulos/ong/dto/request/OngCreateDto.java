package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record OngCreateDto (
//        @NotBlank(message= "Campo 'name' não pode estar vazio")
        String name,
//        @NotBlank(message= "Campo 'status' não pode estar vazio")
        StatusEnum status,
//        @NotBlank(message= "Campo 'cnpj' não pode estar vazio")
        String cnpj,
        PersonEntity responsibleEntity
) {}

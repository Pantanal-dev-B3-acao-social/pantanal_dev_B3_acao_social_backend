package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public record SocialActionUpdateDto(
        @Valid
        @NotNull(message="Campo 'id' não pode estar vazio")
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description

//        @NotBlank(message= "Campo 'organizer' não pode estar vazio")
//        String organizer
) {}

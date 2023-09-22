package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SocialActionDto (
        @Valid
        @NotBlank(message="Campo 'id' não pode estar vazio")
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,

        @NotBlank(message= "Campo 'organizer' não pode estar vazio")
        String organizer,

        @NotNull
        @NotBlank
        long version

) {}

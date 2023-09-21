package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SocialActionDto (
        @NotBlank(message="Campo 'id' não pode estar vazio")
        @NotEmpty(message="Campo 'id' é obrigatório")
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        @NotEmpty(message="Campo 'nome' é obrigatório")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        @NotEmpty(message="Campo 'description' é obrigatório")
        String description,

        @NotBlank(message= "Campo 'organizer' não pode estar vazio")
        @NotEmpty(message="Campo 'organizer' é obrigatório")
        String organizer,

        @NotNull
        @NotBlank
        long version

) {}

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record SocialActionCreateDto(

        @NotBlank(message="Campo 'nome' não pode estar vazio")
        @NotEmpty(message="Campo 'nome' é obrigatório")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        @NotEmpty(message="Campo 'description' é obrigatório")
        String description,

        @NotBlank(message= "Campo 'organizer' não pode estar vazio")
        @NotEmpty(message="Campo 'organizer' é obrigatório")
        String organizer

) {}

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request;
// TODO:
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
public record SocialActionCreateDto(
// TODO:
//        @NotBlank
//        @NotEmpty(message="Campo 'nome' é obrigatório")

        String name,
        String description,

        String organizer

) {}

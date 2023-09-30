package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.UUID;

public record OngCreateDto (
        @NotBlank
        @NotEmpty(message="Campo 'nome' é obrigatório")
        @NotNull
        String name,
        @NotBlank
        @NotEmpty(message="Campo 'cnpj' é obrigatório")
        @NotNull
        //@CNPJ
        String cnpj
//        @NotBlank
//        @NotEmpty(message="Campo 'managerId' é obrigatório")
//        @NotNull
//        UUID managerId
) {}

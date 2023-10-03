package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OngDto (

        @Valid
        @NotBlank(message="Campo 'id' não pode estar vazio")
        UUID id,
        @NotBlank(message="Campo 'nome' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'cnpj' não pode estar vazio")
        String cnpj,

        @NotNull
        @NotBlank
        long version

) {}

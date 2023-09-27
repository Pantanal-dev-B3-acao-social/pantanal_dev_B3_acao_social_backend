package dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CompanyCreateDto (

        @NotBlank(message="Campo 'name' não pode estar vazio")
        String name,
        @NotBlank(message= "Campo 'description' não pode estar vazio")
        String description,
        @NotBlank(message= "Campo 'cnpj' não podes estar vazio")
        String cnpj

) {}

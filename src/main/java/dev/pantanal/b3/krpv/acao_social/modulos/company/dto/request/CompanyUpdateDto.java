package dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record CompanyUpdateDto (
        String name,

        String description,
        @CNPJ
        String cnpj
) {}

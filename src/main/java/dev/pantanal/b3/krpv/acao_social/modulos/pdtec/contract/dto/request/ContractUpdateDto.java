package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request;


import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.enums.StatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record ContractUpdateDto(

        @NotNull
//        @Pattern(regexp = "^(RUNNING|CREATED|DRAFTED|PENDING|CANCELED|DONE|REJECTED|EXPIRATED|EXPIRED)")
        StatusEnum status
) {}

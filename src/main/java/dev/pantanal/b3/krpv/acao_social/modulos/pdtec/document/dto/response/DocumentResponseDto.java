package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.response;

import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.ContractEntity;

import java.util.UUID;

public record DocumentResponseDto (

        UUID id,
        UUID documentPdtecId,
        ContractEntity contract

){
}

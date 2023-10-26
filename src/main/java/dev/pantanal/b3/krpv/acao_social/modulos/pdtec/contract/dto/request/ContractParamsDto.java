package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContractParamsDto(

        UUID companyId,
        UUID socialActionId,
        UUID ongId,
        UUID processId,
        String title,
        String description,
        String justification,
        StatusEnum status,
        LocalDateTime evaluatedAt,
        UUID evaluetedBy

) {
}

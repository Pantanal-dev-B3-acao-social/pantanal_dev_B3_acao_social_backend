package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request;

import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.enums.StatusEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

public record ContractCreateDto (

        @NotNull
        CompanyDto companyId,
        @NotNull
        UUID socialAction,
        @NotNull
        UUID ong,

        String title,

        String description,

        String justification,

        @NotNull
//        @Pattern(regexp = "^(RUNNING|CREATED|DRAFTED|PENDING|CANCELED|DONE|REJECTED|EXPIRATED|EXPIRED)")
        StatusEnum status,

        RequesterDto requester,

        CompanyDto company,

        FlowDto flow,

        List<MemberDto> members


){
    public record RequesterDto(
            UUID id
    ) {}
    public record CompanyDto(
            UUID id
    ) {}
    public record FlowDto(
            boolean defineOrderOfInvolves,
            boolean hasExpiration,
            String expiration,
            boolean readRequired
    ) {}
    public record MemberDto(
            String name,
            String email,
            String documentType,
            String documentCode,
            ActionTypeDto actionType,
            ResponsibilityDto responsibility,
            AuthenticationTypeDto authenticationType,
            int order,
            String type,
            RepresentationDto representation
    ) {}

    public record ActionTypeDto(
            String id
    ) {}

    public record ResponsibilityDto(
            String id
    ) {}

    public record AuthenticationTypeDto(
            String id
    ) {}

    public record RepresentationDto(
            boolean willActAsPhysicalPerson,
            boolean willActRepresentingAnyCompany,
            List<CompanyDto> companies
    ) {}
}

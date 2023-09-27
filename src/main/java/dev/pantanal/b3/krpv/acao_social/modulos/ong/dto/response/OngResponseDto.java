package dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.response;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record OngResponseDto (
    UUID id,
    @NotNull
    String name,
    String cnpj,
    UUID managerId,
    UUID createdBy,
    @Nullable
    UUID lastModifiedBy,
    @Nullable
    LocalDateTime createdDate,
    @Nullable
    LocalDateTime lastModifiedDate,
    @Nullable
    LocalDateTime deletedDate,
    @Nullable
    UUID deletedBy
) {}

package dev.pantanal.b3.krpv.acao_social.dto.request;

import java.util.UUID;

//import javax.validation.constraints.Null;
public record SocialActionUpdateDto(
//        @Null
        UUID id,
        String name,
        String description
) {}

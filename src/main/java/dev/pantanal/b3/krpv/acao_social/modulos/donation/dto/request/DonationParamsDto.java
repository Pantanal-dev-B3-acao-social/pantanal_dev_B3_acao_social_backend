package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

public record DonationParamsDto (

        @Valid
        LocalDateTime donation_date,
        Double value_money,
        String motivation

){}

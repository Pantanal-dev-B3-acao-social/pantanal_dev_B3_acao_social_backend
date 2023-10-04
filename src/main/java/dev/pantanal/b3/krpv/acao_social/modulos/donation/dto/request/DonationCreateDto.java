package dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DonationCreateDto (
        @Valid
        @NotBlank(message="Campo 'donation_date' não pode estar vazio")
        LocalDateTime donation_date,
        @NotBlank(message= "Campo 'value_money' não pode estar vazio")
        Double value_money,
        @NotBlank(message= "Campo 'motivation' não pode estar vazio")
        String motivation
){}

package dev.pantanal.b3.krpv.acao_social.controller;

import dev.pantanal.b3.krpv.acao_social.dto.SocialActionDto;
import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.dto.response.SocialActionResponseDto;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.service.SocialActionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/v1/social")
public class SocialActionController {

    @Autowired
    private SocialActionService service;

    @GetMapping
//    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok("todos os usuarios");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Cria um endereço", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrada"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro ao criar endereço"),
    })
    public ResponseEntity<SocialActionResponseDto> create(@Valid @RequestBody SocialActionCreateDto request) {
        SocialActionEntity entity = service.create(request);
        SocialActionResponseDto response = new SocialActionResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
        // TODO: fazer um handle para gerar esse retorno
    }

}

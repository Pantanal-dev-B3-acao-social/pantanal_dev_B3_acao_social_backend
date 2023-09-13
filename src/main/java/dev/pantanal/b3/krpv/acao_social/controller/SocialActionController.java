package dev.pantanal.b3.krpv.acao_social.controller;

import dev.pantanal.b3.krpv.acao_social.dto.SocialActionDto;
import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.dto.request.SocialActionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.dto.response.SocialActionResponseDto;
import dev.pantanal.b3.krpv.acao_social.entity.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.service.SocialActionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// TODO: import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import static dev.pantanal.b3.krpv.acao_social.controller.SocialActionController.ROUTE_SOCIAL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RestController
@RequestMapping(ROUTE_SOCIAL)
public class SocialActionController {

    @Autowired
    private SocialActionService service;
    public static final String ROUTE_SOCIAL = "/v1/social";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<SocialActionEntity> findAll(Pageable pageable, @Valid SocialActionParamsDto request) {
        Page<SocialActionEntity> entities = service.findAll(pageable, request);
        return entities;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SocialActionEntity> findOne(@PathVariable UUID id) {
        SocialActionEntity entity = service.findById(id);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            // Aqui você pode personalizar a resposta de acordo com o que desejar
            // Por exemplo, retornar 404 Not Found se a ação social não for encontrada
            return ResponseEntity.notFound().build();
        }
//        return new ResponseEntity<SocialActionEntity>(entity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyRole('ADMIN')")
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
        // TODO: fazer um handle para gerar esse retorno
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SocialActionResponseDto> update(@Valid @RequestBody SocialActionUpdateDto request){
        SocialActionEntity entity = service.update(request); //Montar metodo update no Service
        SocialActionResponseDto response = new SocialActionResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        //Verificar erro aqui
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma ação social por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ação social excluída com sucesso"),
            @ApiResponse(responseCode = "400", description = "ID inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Ação social não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro ao excluir ação social"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}

package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.response.OngResponseDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import static dev.pantanal.b3.krpv.acao_social.modulos.ong.OngController.ROUTE_ONG;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

@RestController
@RequestMapping(ROUTE_ONG)
public class OngController {

    @Autowired
    private OngService service;
    public static final String ROUTE_ONG = "/v1/ong";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets Ongs", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<OngEntity> findAll(Pageable pageable, @Valid OngParamsDto request) {
        Page<OngEntity> entities = service.findAll(pageable, request);
        return entities;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets one Ong", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<OngEntity> findOne(@PathVariable UUID id) {
        OngEntity entity = service.findById(id);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ONG_CREATE')")
    @Operation(summary = "Creates a Ong", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ong succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Ong not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating ong"),
    })
    public ResponseEntity<OngResponseDto> create(@Valid @RequestBody OngCreateDto request) {
        OngEntity entity = service.create(request);
        OngResponseDto response = new OngResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getCnpj(),
                entity.getManagerId(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{ongId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates a Ong", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Ong no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating ong"),
    })
    public ResponseEntity<OngResponseDto> update(@PathVariable UUID ongId, @Valid @RequestBody OngUpdateDto request){
        OngEntity entity = service.update(ongId, request);
        OngResponseDto response = new OngResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getCnpj(),
                entity.getManagerId(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a Ong", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Ong not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding ong"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }


}

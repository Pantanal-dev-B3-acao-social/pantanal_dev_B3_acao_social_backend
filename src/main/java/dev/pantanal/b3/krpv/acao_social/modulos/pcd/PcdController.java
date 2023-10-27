package dev.pantanal.b3.krpv.acao_social.modulos.pcd;


import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.response.PcdResponseDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.UUID;


import static dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdController.ROUTE_PCD;

@RestController
@RequestMapping(ROUTE_PCD)
@PreAuthorize("hasAnyRole('PCD')")
public class PcdController {

    @Autowired
    private PcdService service;
    public static final String ROUTE_PCD = "/v1/pcd";

    public PcdResponseDto mapEntityToDto(PcdEntity entity) {
        PcdResponseDto dto = new PcdResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getObservation(),
                entity.getCode(),
                entity.getAcronym(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return dto;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('PCD_GET_ALL')")
    @Operation(summary = "Gets Pcds", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<PcdResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid PcdParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<PcdEntity> entities = service.findAll(paging, request);
        List<PcdResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('PCD_GET_ONE')")
    @Operation(summary = "Gets one Pcd", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<PcdResponseDto> findOne(@PathVariable UUID id) {
        PcdEntity entity = service.findById(id);
        PcdResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<PcdResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('PCD_CREATE')")
    @Operation(summary = "Creates an Pcd", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Social action succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<PcdResponseDto> create(@RequestBody @Valid PcdCreateDto request) {
        PcdEntity entity = service.create(request);
        PcdResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Pcd", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Pcd no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<PcdResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PcdUpdateDto request) {
        PcdEntity entity = service.update(id, request);
        PcdResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an Pcd", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding social action"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}

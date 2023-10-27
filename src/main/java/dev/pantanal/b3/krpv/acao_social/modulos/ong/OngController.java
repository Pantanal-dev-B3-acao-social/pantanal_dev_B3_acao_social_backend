package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.request.OngUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.response.OngResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionService;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.dto.response.OngResponseDto;
import io.swagger.v3.oas.annotations.Operation;
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

import java.util.List;

import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.ong.OngController.ROUTE_ONG;

@RestController
@RequestMapping(ROUTE_ONG)
public class OngController {

    @Autowired
    private OngService service;
    public static final String ROUTE_ONG = "/v1/ong";


    public OngResponseDto mapEntityToDto(OngEntity entity) {
        OngResponseDto dto = new OngResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                entity.getCnpj(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy(),
                entity.getResponsibleEntity()
        );
        return dto;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ONG_GET_ALL')")
    @Operation(summary = "Gets Ongs", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<OngResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid OngParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<OngEntity> pages = service.findAll(paging, request);
        List<OngResponseDto> response = pages.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(response, paging, pages.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ONG_GET_ONE')")
    @Operation(summary = "Gets one Ong", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<OngResponseDto> findOne(@PathVariable UUID id) {
        OngEntity entity = service.findById(id);
        OngResponseDto response = new OngResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                entity.getCnpj(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy(),
                entity.getResponsibleEntity()
        );
        return new ResponseEntity<OngResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ONG_CREATE')")
    @Operation(summary = "Creates an Ong", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Social action succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<OngResponseDto> create(@RequestBody @Valid OngCreateDto request) {
        OngEntity entity = service.create(request);
        OngResponseDto response = new OngResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                entity.getCnpj(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy(),
                entity.getResponsibleEntity()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ONG_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Ong", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Ong no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<OngResponseDto> update(@PathVariable UUID id, @Valid @RequestBody OngUpdateDto request) {
        OngEntity entity = service.update(id, request);
        OngResponseDto response = new OngResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                entity.getCnpj(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy(),
                entity.getResponsibleEntity()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ONG_DELETE')")
    @Operation(summary = "Deletes an Ong", method = "DELETE")
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

package dev.pantanal.b3.krpv.acao_social.modulos.pcd;


import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdPersonCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdPersonParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.request.PcdPersonUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcd.dtos.response.PcdPersonResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.PcdPersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.PcdPersonService;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request.PcdPersonCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request.PcdPersonParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.request.PcdPersonUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pcdPerson.dtos.response.PcdPersonResponseDto;
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


import static dev.pantanal.b3.krpv.acao_social.modulos.pcd.PcdPersonController.ROUTE_PCDPERSON;

@RestController
@RequestMapping(ROUTE_PCDPERSON)
//@PreAuthorize("hasAnyRole('PCDPERSON')")
public class PcdPersonController {

    @Autowired
    private PcdPersonService service;
    public static final String ROUTE_PCDPERSON = "/v1/pcdPerson";

    public PcdPersonResponseDto mapEntityToDto(PcdPersonEntity entity) {
        PcdPersonResponseDto dto = new PcdPersonResponseDto(
                entity.getId(),
                entity.getPerson(),
                entity.getPcd(),
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
//    @PreAuthorize("hasAnyRole('PCDPERSON_GET_ALL')")
    @Operation(summary = "Gets PcdPersons", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<PcdPersonResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid PcdPersonParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<PcdPersonEntity> entities = service.findAll(paging, request);
        List<PcdPersonResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAnyRole('PCDPERSON_GET_ONE')")
    @Operation(summary = "Gets one PcdPerson", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<PcdPersonResponseDto> findOne(@PathVariable UUID id) {
        PcdPersonEntity entity = service.findById(id);
        PcdPersonResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<PcdPersonResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAnyRole('PCDPERSON_CREATE')")
    @Operation(summary = "Creates an PcdPerson", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pcd_Person succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Pcd_Person not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<PcdPersonResponseDto> create(@RequestBody @Valid PcdPersonCreateDto request) {
        PcdPersonEntity entity = service.create(request);
        PcdPersonResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an PcdPerson", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "PcdPerson no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<PcdPersonResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PcdPersonUpdateDto request) {
        PcdPersonEntity entity = service.update(id, request);
        PcdPersonResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an PcdPerson", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Pcd_Person not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding social action"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}

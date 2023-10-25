package dev.pantanal.b3.krpv.acao_social.modulos.voluntary;

import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.response.VoluntaryResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request.VoluntaryCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request.VoluntaryParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.request.VoluntaryUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.dto.response.VoluntaryResponseDto;
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
import java.util.stream.Collectors;

import static dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryController.ROUTE_VOLUNTARY;

@RestController
@RequestMapping(ROUTE_VOLUNTARY)
@PreAuthorize("hasAnyRole('VOLUNTARY')")
public class VoluntaryController {
     @Autowired
    private VoluntaryService service;

    public static final String ROUTE_VOLUNTARY = "/v1/voluntary";

    public VoluntaryResponseDto mapEntityToDto(VoluntaryEntity entity) {
        VoluntaryResponseDto dto = new VoluntaryResponseDto(
                entity.getId(),
                entity.getObservation(),
                entity.getSocialAction(),
                entity.getPerson(),
                entity.getStatus(),
                entity.getApprovedBy(),
                entity.getApprovedDate(),
                entity.getFeedbackScoreVoluntary(),
                entity.getFeedbackVoluntary(),
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
    @PreAuthorize("hasAnyRole('VOLUNTARY_GET_ALL')")
    @Operation(summary = "Gets Sessions", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<VoluntaryResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="feedbackScoreVoluntary", direction = Sort.Direction.DESC) Sort sort,
            @Valid VoluntaryParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<VoluntaryEntity> entities = service.findAll(paging, request);
        List<VoluntaryResponseDto> responseDto = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(responseDto, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('VOLUNTARY_GET_ONE')")
    @Operation(summary = "Gets one Session", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<VoluntaryResponseDto> findOne(@PathVariable UUID id) {
        VoluntaryEntity entity = service.findById(id);
        VoluntaryResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<VoluntaryResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('VOLUNTARY_CREATE')")
    @Operation(summary = "Creates an Session", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voluntary succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Voluntary not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating voluntary"),
    })
    public ResponseEntity<VoluntaryResponseDto> create(@RequestBody @Valid VoluntaryCreateDto request) {
        VoluntaryEntity entity = service.create(request);
        VoluntaryResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('VOLUNTARY_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Session", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Session no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating voluntary"),
    })
    public ResponseEntity<VoluntaryResponseDto> update(@PathVariable UUID id, @Valid @RequestBody VoluntaryUpdateDto request) {
        VoluntaryEntity entity = service.update(id, request);
        VoluntaryResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('VOLUNTARY_DELETE')")
    @Operation(summary = "Deletes an Session", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Voluntary not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding voluntary"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}

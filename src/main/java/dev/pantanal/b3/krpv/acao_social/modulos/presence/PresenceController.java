package dev.pantanal.b3.krpv.acao_social.modulos.presence;

import dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.request.PresenceCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.request.PresenceParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.dto.response.PresenceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceController.ROUTE_PRESENCE;

@RestController
@RequestMapping(ROUTE_PRESENCE)
public class PresenceController {

    @Autowired
    private PresenceService service;
    public static final String ROUTE_PRESENCE = "/v1/presence";


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('PRESENCE_GET_ALL')")
    @Operation(summary = "Gets Sessions", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<PresenceEntity> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid PresenceParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<PresenceEntity> response = service.findAll(paging, request);
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('PRESENCE_GET_ONE')")
    @Operation(summary = "Gets one Session", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<PresenceResponseDto> findOne(@PathVariable UUID id) {
        PresenceEntity entity = service.findById(id);
        PresenceResponseDto response = new PresenceResponseDto(
                entity.getId(),
                entity.getPerson(),
                entity.getSession(),
                entity.getApprovedBy(),
                entity.getApprovedDate(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<PresenceResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('PRESENCE_CREATE')")
    @Operation(summary = "Creates an Session", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Presence succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Presence not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating voluntary"),
    })
    public ResponseEntity<PresenceResponseDto> create(@RequestBody @Valid PresenceCreateDto request) {
        PresenceEntity entity = service.create(request);
        PresenceResponseDto response = new PresenceResponseDto(
                entity.getId(),
                entity.getPerson(),
                entity.getSession(),
                entity.getApprovedBy(),
                entity.getApprovedDate(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('PRESENCE_DELETE')")
    @Operation(summary = "Deletes an Session", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Presence not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding voluntary"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}

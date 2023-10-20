package dev.pantanal.b3.krpv.acao_social.modulos.session;

import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request.SessionCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request.SessionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.request.SessionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.session.dto.response.SessionResponseDto;
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

import static dev.pantanal.b3.krpv.acao_social.modulos.session.SessionController.ROUTE_SESSION;

@RestController
@RequestMapping(ROUTE_SESSION)
public class SessionController {

    @Autowired
    private SessionService service;

    public static final String ROUTE_SESSION = "/v1/session";

        public SessionResponseDto mapEntityToDto(SessionEntity entity) {
        SessionResponseDto dto = new SessionResponseDto(
                entity.getId(),
                entity.getDescription(),
                entity.getSocialAction(),
                entity.getDateStartTime(),
                entity.getDateEndTime(),
                entity.getStatus(),
                entity.getVisibility(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedBy(),
                entity.getDeletedDate()
        );
        return dto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('SESSION_GET_ALL')")
    @Operation(summary = "Gets Sessions", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<SessionResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid SessionParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<SessionEntity> entities = service.findAll(paging, request);
        List<SessionResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('SESSION_GET_ONE')")
    @Operation(summary = "Gets one Session", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<SessionResponseDto> findOne(@PathVariable UUID id) {
        SessionEntity entity = service.findById(id);
        SessionResponseDto response= mapEntityToDto(entity);
        return new ResponseEntity<SessionResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('SESSION_CREATE')")
    @Operation(summary = "Creates an Session", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Social action succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<SessionResponseDto> create(@RequestBody @Valid SessionCreateDto request) {
        SessionEntity entity = service.create(request);
        SessionResponseDto response= mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('SESSION_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Session", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Session no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<SessionResponseDto> update(@PathVariable UUID id, @Valid @RequestBody SessionUpdateDto request) {
        SessionEntity entity = service.update(id, request);
        SessionResponseDto response= mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('SESSION_DELETE')")
    @Operation(summary = "Deletes an Session", method = "DELETE")
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

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.response.SocialActionResponseDto;
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
import static dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionController.ROUTE_SOCIAL;
import static dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionController.SOFT_DELETE_QUERY;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ROUTE_SOCIAL)
@PreAuthorize("hasAnyRole('SOCIAL_ACTION')")
public class SocialActionController {

    @Autowired
    private SocialActionService service;
    public static final String SOFT_DELETE_QUERY = "";
    public static final String ROUTE_SOCIAL = "/v1/social";

    public SocialActionResponseDto mapEntityToDto(SocialActionEntity entity) {
//        List<UUID> categoryTypeIds = entity.getCategorySocialActionTypeEntities().stream()
//                .map(type -> type.getId())
//                .collect(Collectors.toList());
//        List<UUID> categoryLevelIds = entity.getCategorySocialActionLevelEntities().stream()
//                .map(level -> level.getId())
//                .collect(Collectors.toList());
        SocialActionResponseDto dto = new SocialActionResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getSessionsEntities(),
                entity.getVoluntaryEntities(),
                entity.getCategorySocialActionTypeEntities(),
                entity.getCategorySocialActionLevelEntities(),
                entity.getOng(),
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
    @PreAuthorize("hasAnyRole('SOCIAL_ACTION_GET_ALL')")
    @Operation(summary = "Gets Social Actions", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<SocialActionResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid SocialActionParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<SocialActionEntity> entities = service.findAll(paging, request);
        List<SocialActionResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('SOCIAL_ACTION_GET_ONE')")
    @Operation(summary = "Gets one Social Action", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<SocialActionResponseDto> findOne(@PathVariable UUID id) {
        SocialActionEntity entity = service.findById(id);
        SocialActionResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<SocialActionResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('SOCIAL_ACTION_CREATE')")
    @Operation(summary = "Creates an Social Action", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Social action succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<SocialActionResponseDto> create(@RequestBody @Valid SocialActionCreateDto request) {
        SocialActionEntity entity = service.create(request);
        SocialActionResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Social Action", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social Action no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<SocialActionResponseDto> update(@PathVariable UUID id, @Valid @RequestBody SocialActionUpdateDto request) {
        SocialActionEntity entity = service.update(id, request);
        List<UUID> categoryTypeIds = entity.getCategorySocialActionTypeEntities().stream()
                .map(type -> type.getId())
                .collect(Collectors.toList());
        List<UUID> categoryLevelIds = entity.getCategorySocialActionLevelEntities().stream()
                .map(level -> level.getId())
                .collect(Collectors.toList());
        SocialActionResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an Social Action", method = "DELETE")
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

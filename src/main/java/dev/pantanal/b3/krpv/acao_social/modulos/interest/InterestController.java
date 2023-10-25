package dev.pantanal.b3.krpv.acao_social.modulos.interest;

import dev.pantanal.b3.krpv.acao_social.modulos.interest.InterestEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.InterestService;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.request.InterestCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.request.InterestParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.response.InterestResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.response.SocialActionResponseDto;
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

import static dev.pantanal.b3.krpv.acao_social.modulos.interest.InterestController.ROUTE_INTEREST;

@RestController
@RequestMapping(ROUTE_INTEREST)
public class InterestController {

    @Autowired
    private InterestService service;
    public static final String ROUTE_INTEREST = "/v1/interest";

    public InterestResponseDto mapEntityToDto(InterestEntity entity) {
        InterestResponseDto dto = new InterestResponseDto(
                entity.getId(),
                entity.getPerson(),
                entity.getCategory(),
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
    //@PreAuthorize("hasAnyRole('INTEREST_GET_ALL')")
    @Operation(summary = "Gets Interest", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<InterestResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid InterestParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<InterestEntity> entities = service.findAll(paging, request);
        List<InterestResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos,paging,entities.getTotalElements());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('INTEREST_GET_ONE')")
    @Operation(summary = "Gets one Interest", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<InterestResponseDto> findOne(@PathVariable UUID id) {
        InterestEntity entity = service.findById(id);
        InterestResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<InterestResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('INTEREST_CREATE')")
    @Operation(summary = "Creates an Interest", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Interest succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Interest not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating interest"),
    })
    public ResponseEntity<InterestResponseDto> create(@RequestBody @Valid InterestCreateDto request) {
        InterestEntity entity = service.create(request);
        InterestResponseDto response = mapEntityToDto(entity);
        return new ResponseEntity<InterestResponseDto>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasAnyRole('INTEREST_DELETE')")
    @Operation(summary = "Deletes an Interest", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Interest not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding interest"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}

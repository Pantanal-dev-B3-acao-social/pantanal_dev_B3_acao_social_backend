package dev.pantanal.b3.krpv.acao_social.modulos.person;

import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.request.PersonUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.person.dto.response.PersonResponseDto;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static dev.pantanal.b3.krpv.acao_social.modulos.person.PersonController.ROUTE_PERSON;

@RestController
@RequestMapping(ROUTE_PERSON)
public class PersonController {
    @Autowired
    private PersonService service;

    public static final String ROUTE_PERSON = "/v1/person";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('PERSON_GET_ALL')")
    @Operation(summary = "Gets Persons", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<PersonEntity> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid PersonParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<PersonEntity> response = service.findAll(paging, request);
        return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('PERSON_GET_ONE')")
    @Operation(summary = "Gets one Person", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<PersonResponseDto> findOne(@PathVariable UUID id) {
        PersonEntity entity = service.findById(id);
        PersonResponseDto response = new PersonResponseDto(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getDateBirth(),
                entity.getStatus(),
                entity.getCpf(),
                entity.getEngagementScore(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<PersonResponseDto>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('PERSON_CREATE')")
    @Operation(summary = "Creates an Person", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Social action succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social action not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<PersonResponseDto> create(JwtAuthenticationToken userLogged, @RequestBody @Valid PersonCreateDto request) {
        PersonEntity entity = service.create(request, userLogged.getToken().getTokenValue());
        PersonResponseDto response = new PersonResponseDto(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getDateBirth(),
                entity.getStatus(),
                entity.getCpf(),
                entity.getEngagementScore(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('PERSON_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Person", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Person no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating social action"),
    })
    public ResponseEntity<PersonResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PersonUpdateDto request) {
        PersonEntity entity = service.update(id, request);
        PersonResponseDto response = new PersonResponseDto(
                entity.getId(),
                entity.getUserId(),
                entity.getName(),
                entity.getDateBirth(),
                entity.getStatus(),
                entity.getCpf(),
                entity.getEngagementScore(),
                entity.getCreatedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('PERSON_DELETE')")
    @Operation(summary = "Deletes an Person", method = "DELETE")
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

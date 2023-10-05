package dev.pantanal.b3.krpv.acao_social.modulos.Investment;

import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.response.InvestmentResponseDto;
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

import static dev.pantanal.b3.krpv.acao_social.modulos.Investment.InvestmentController.ROUTE_INVESTMENT;

@RestController
@RequestMapping(ROUTE_INVESTMENT)
public class InvestmentController {
    @Autowired
    private InvestmentService service;
    public static final String ROUTE_INVESTMENT = "/v1/investment";

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAnyRole('INVESTMENT_GET_ONE')")
    @Operation(summary = "Gets one Category", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<InvestmentResponseDto> findOne(@PathVariable UUID id) {
        InvestmentEntity entity = service.findById(id);
        InvestmentResponseDto response = new InvestmentResponseDto(
                entity.getId(),
                entity.getValue_money(),
                entity.getDate(),
                entity.getMotivation(),
                entity.getApprovedAt(),
                entity.getSocialAction(),
                entity.getCompany(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return new ResponseEntity<InvestmentResponseDto>(response, HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAnyRole('INVESTMENT_GET_ALL')")
    @Operation(summary = "Gets Investments", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element(s) found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public Page<InvestmentEntity> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid InvestmentParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<InvestmentEntity> response = service.findAll(paging, request);
        return response; // TODO: verificar se vai converter certo
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('INVESTMENT_CREATE')")
    @Operation(summary = "Creates an Investment", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Investment succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Investment not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating company"),
    })
    public ResponseEntity<InvestmentResponseDto> create(@RequestBody @Valid InvestmentCreateDto request){
        InvestmentEntity entity = service.create(request);
        InvestmentResponseDto response = new InvestmentResponseDto(
                entity.getId(),
                entity.getValue_money(),
                entity.getDate(),
                entity.getMotivation(),
                entity.getApprovedAt(),
                entity.getSocialAction(),
                entity.getCompany(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()

        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    //@PreAuthorize("hasAnyRole('INVESTMENT_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Category", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Investment no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating Investment"),
    })
    public ResponseEntity<InvestmentResponseDto> update(@PathVariable UUID id, @Valid @RequestBody InvestmentUpdateDto request) {
        InvestmentEntity entity = service.update(id, request);
        InvestmentResponseDto response = new InvestmentResponseDto(
                id,
                entity.getValue_money(),
                entity.getDate(),
                entity.getMotivation(),
                entity.getApprovedAt(),
                entity.getSocialAction(),
                entity.getCompany(),
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
    //@PreAuthorize("hasAnyRole('INVESTMENT_DELETE')")
    @Operation(summary = "Deletes an Investment", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Investment not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding Investment"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}

package dev.pantanal.b3.krpv.acao_social.modulos.investment;

import dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request.InvestmentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request.InvestmentParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.request.InvestmentUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.dto.response.InvestmentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.investment.InvestmentController.ROUTE_INVESTMENT;

@RestController
@RequestMapping(ROUTE_INVESTMENT)
public class InvestmentController {
    @Autowired
    private InvestmentService service;
    public static final String ROUTE_INVESTMENT = "/v1/investment";

    public InvestmentResponseDto mapEntityToDto(InvestmentEntity entity) {
        InvestmentResponseDto dto = new InvestmentResponseDto(
                entity.getId(),
                entity.getValueMoney(),
                entity.getDate(),
                entity.getMotivation(),
                entity.getApprovedBy().getId(),
                entity.getApprovedDate(),
                entity.getSocialAction().getId(),
                entity.getCompany().getId(),
                entity.getCreatedBy(),
                entity.getLastModifiedBy(),
                entity.getCreatedDate(),
                entity.getLastModifiedDate(),
                entity.getDeletedDate(),
                entity.getDeletedBy()
        );
        return dto;
    }

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
        InvestmentResponseDto response = mapEntityToDto(entity);
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
    public Page<InvestmentResponseDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort,
            @Valid InvestmentParamsDto request
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<InvestmentEntity> entities = service.findAll(paging, request);
        List<InvestmentResponseDto> dtos = entities.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(dtos, paging, entities.getTotalElements());
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
        InvestmentResponseDto response = mapEntityToDto(entity);
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
        InvestmentResponseDto response = mapEntityToDto(entity);
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

package dev.pantanal.b3.krpv.acao_social.modulos.company;

import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request.CompanyCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.response.CompanyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyController.ROUTE_COMPANY;

@RestController
@RequestMapping(ROUTE_COMPANY)
public class CompanyController {
    @Autowired
    private CompanyService service;
    public static final String ROUTE_COMPANY = "/v1/company";

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('COMPANY_GET_ONE')")
    @Operation(summary = "Gets one Company", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found successfully"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    public ResponseEntity<CompanyResponseDto> findOne(@PathVariable UUID id) {
        CompanyEntity entity = service.findById(id);
        CompanyResponseDto response = new CompanyResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCnpj(),
                entity.getVersion()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('COMPANY_CREATE')")
    @Operation(summary = "Creates an Company", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Company not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating company"),
    })
    public ResponseEntity<CompanyResponseDto> create(@RequestBody @Valid CompanyCreateDto request) {
        CompanyEntity entity = service.create(request);
        CompanyResponseDto response = new CompanyResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCnpj(),
                entity.getVersion()
        );
        // TODO: fazer um handle para gerar esse retorno
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.PdtecClient;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.response.ContractResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.enums.StatusEnum;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.ContractController.ROUTE_CONTRACT;

@RequestMapping(ROUTE_CONTRACT)
@RestController
public class ContractController {
    @Autowired
    private ContractService service;
    @Autowired
    private PdtecClient pdtecClient;
    @Autowired
    ObjectMapper objectMapper;

    public static final String ROUTE_CONTRACT = "/v1/contract";

    public ContractResponseDto mapEntityToDto(ContractEntity response) {
        ContractResponseDto dtos = new ContractResponseDto(

                response.getId(),
                response.getCompany(),
                response.getSocialAction(),
                response.getOng(),
                response.getProcessId(),
                response.getTitle(),
                response.getDescription(),
                response.getJustification(),
                response.getStatus(),
                response.getEvaluatedAt(),
                response.getEvaluatedBy(),
                response.getCreatedBy(),
                response.getLastModifiedBy(),
                response.getCreatedDate(),
                response.getLastModifiedDate(),
                response.getDeletedDate(),
                response.getDeletedBy()
        );

        return dtos;
    }


    @GetMapping("/{contractId}")
    @PreAuthorize("hasAnyRole('USER_GET_ONE')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ContractResponseDto> findById(JwtAuthenticationToken contractLogged, @PathVariable UUID contractId) {
        ContractEntity contract = this.service.findById(contractId);
        ContractResponseDto response = mapEntityToDto(contract);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER_GET_ALL')")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContractResponseDto> findAll (
            @RequestBody @Valid ContractParamsDto params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<ContractEntity> pages = this.service.findAll(paging, params);
        List<ContractResponseDto> results = pages.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(results, paging, results.size());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('USER_CREATE')")
    @Operation(summary = "Creates an Contract", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contract succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "Contract not authenticated"),
            @ApiResponse(responseCode = "404", description = "Contract not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating Contract"),
    })
    public ResponseEntity<ContractResponseDto> create(
            @RequestBody @Valid ContractCreateDto request
    ) {
        String token = pdtecClient.getAccessToken();
        ContractEntity contract = service.create(request, token);
        ContractResponseDto response = mapEntityToDto(contract);
        return new ResponseEntity<ContractResponseDto>(response, HttpStatus.OK);
    }

//    @PatchMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    @Operation(summary = "Updates an Contract", method = "PATCH")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
//            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
//            @ApiResponse(responseCode = "401", description = "Contract is not authenticated"),
//            @ApiResponse(responseCode = "404", description = "Contract no found"),
//            @ApiResponse(responseCode = "422", description = "Invalid request data"),
//            @ApiResponse(responseCode = "500", description = "Error when creating Contract"),
//    })
//    public ResponseEntity<String> update(
//            @PathVariable UUID id,
//            @Valid @RequestBody ContractUpdateDto dto
//    ) {
//        ResponseEntity<String> response = service.update(id, dto);
//        return response;
//    }
//
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an contract", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "Contract not authenticated"),
            @ApiResponse(responseCode = "404", description = "Contract not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding Contract"),
    })
    public void delete(
            @PathVariable UUID id
    ) {
        service.delete(id);
    }

}


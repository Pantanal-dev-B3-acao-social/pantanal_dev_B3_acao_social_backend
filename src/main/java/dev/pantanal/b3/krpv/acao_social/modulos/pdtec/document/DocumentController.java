package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.PdtecClient;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.DocumentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.DocumentUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.response.DocumentResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.DocumentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.response.DocumentResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.response.DocumentResponseDto;
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

import static dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.DocumentController.ROUTE_DOCUMENT;

@RequestMapping(ROUTE_DOCUMENT)
@RestController
public class DocumentController {
    @Autowired
    private DocumentService service;
    @Autowired
    private PdtecClient pdtecClient;
    @Autowired
    ObjectMapper objectMapper;

    public static final String ROUTE_DOCUMENT = "/v1/document";

    public DocumentResponseDto mapEntityToDto(DocumentEntity response) {
        DocumentResponseDto dtos = new DocumentResponseDto(

                response.getId(),
                response.getPdtecDocumentId(),
                response.getContract()
        );

        return dtos;
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('USER_GET_ONE')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DocumentResponseDto> findById(@PathVariable UUID documentId) {
        DocumentEntity document = this.service.findById(documentId);
        DocumentResponseDto response = mapEntityToDto(document);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    //@PreAuthorize("hasAnyRole('USER_GET_ALL')")
    @ResponseStatus(HttpStatus.OK)
    public Page<DocumentResponseDto> findAll (
            @Valid DocumentParamsDto params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        Page<DocumentEntity> pages = this.service.findAll(paging, params);
        List<DocumentResponseDto> results = pages.map(this::mapEntityToDto).getContent();
        return new PageImpl<>(results, paging, results.size());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@PreAuthorize("hasAnyRole('USER_CREATE')")
    @Operation(summary = "Creates an Document", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Document succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "Document not authenticated"),
            @ApiResponse(responseCode = "404", description = "Document not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating Document"),
    })
    public ResponseEntity<DocumentResponseDto> create(
            @RequestBody @Valid DocumentCreateDto request
    ) {
        String token = pdtecClient.getAccessToken();
        DocumentEntity contract = service.create(request, token);
        DocumentResponseDto response = mapEntityToDto(contract);
        return new ResponseEntity<DocumentResponseDto>(response, HttpStatus.OK);
    }

    public DocumentResponseDto update(
            @PathVariable UUID id,
            @Valid @RequestBody DocumentUpdateDto dto
    ) {
        DocumentEntity document = service.update(id, dto);
        DocumentResponseDto response = mapEntityToDto(document);
        return response;
    }
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

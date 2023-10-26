package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.PdtecClient;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.response.DocumentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}

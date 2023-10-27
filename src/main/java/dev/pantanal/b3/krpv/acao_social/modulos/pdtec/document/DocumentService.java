package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.ContractEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository.ContractRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.repository.DocumentPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository repository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private DocumentPredicates documentPredicates;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();
    @Value("${acao-social.pdtec.baseUrl}")
    private String pdtecUrl;

    @Value("${acao-social.pdtec.x-tenant}")
    private String xTenant;

    public DocumentEntity create(DocumentCreateDto request, String token) {
        ContractEntity contract = contractRepository.findById(request.contractId());
        if (contract == null){
            throw new ObjectNotFoundException("Invalid Contract Id");
        }
        String requestUrlEndpoint = String.format("%s/processes/%s/documents", pdtecUrl, contract.getProcessId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("x-Tenant", xTenant);

        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("extension", request.extension());
        makeBody.put("isPendency", request.isPendency());
        makeBody.put("name", request.name());
        makeBody.put("order", request.order());
        makeBody.put("type", request.type());

        String jsonRequest;

        try {
            jsonRequest = objectMapper.writeValueAsString(makeBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpEntity<String> requestPdtec = new HttpEntity<>(jsonRequest, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responsePdtec = restTemplate.exchange(
                requestUrlEndpoint,
                HttpMethod.POST,
                requestPdtec,
                String.class
        );
        String responseBody = responsePdtec.getBody();
        JsonNode responseJson;
        try {
            responseJson = objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String responseId = responseJson.get("id").asText();
        if (responseId != null) {

            DocumentEntity newDocument = new DocumentEntity();
            newDocument.setContract(contract);
            newDocument.setPdtecDocumentId(UUID.fromString(responseId));
            DocumentEntity savedDocument = repository.save(newDocument);
            return savedDocument;
        } else {
            throw new ObjectNotFoundException("Invalid request, could not create proccess");
        }
    }

    public Page<DocumentEntity> findAll(Pageable paging, DocumentParamsDto filters) {
        BooleanExpression predicate = documentPredicates.buildPredicate(filters);
        Page<DocumentEntity> objects = repository.findAll(paging, predicate);
        return objects;
    }

    public DocumentEntity findById(UUID id) {
        DocumentEntity obj = repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public void upload(UUID id, String token, MultipartFile file) {
        DocumentEntity document = repository.findById(id);
        if (document == null){
            throw new ObjectNotFoundException("Invalid Contract Id");
        }
        String requestUrlEndpoint = String.format("%s/processes/%s/documents/%s/upload", pdtecUrl, document.getContract().getProcessId(), document.getPdtecDocumentId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(token);
        headers.set("x-Tenant", xTenant);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestPdtec = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responsePdtec = restTemplate.exchange(
                requestUrlEndpoint,
                HttpMethod.POST,
                requestPdtec,
                String.class
        );
    }

    public void delete(UUID id) { repository.delete(id); }


}

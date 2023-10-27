package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.ContractEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository.ContractRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.dto.request.DocumentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.repository.DocumentRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}

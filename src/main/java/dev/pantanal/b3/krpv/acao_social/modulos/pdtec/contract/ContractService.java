package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractCreateDto;
//import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository.ContractPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository.ContractPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.repository.ContractRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository;
    @Autowired
    private SocialActionRepository socialActionRepository;
    @Autowired
    private OngRepository ongRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ContractPredicates contractPredicates;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Value("${acao-social.pdtec.baseUrl}")
    private String pdtecUrl;

    @Value("${acao-social.pdtec.x-tenant}")
    private String xTenant;

    public ContractEntity create(ContractCreateDto request, String token) {
        String requestUrlEndpoint = pdtecUrl + "/processes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("x-Tenant", xTenant);

        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("title", request.title());
        makeBody.put("company", request.company());
        makeBody.put("requester", request.requester());
        makeBody.put("flow", request.flow());
        makeBody.put("members", request.members());
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

            ContractEntity newContract = new ContractEntity();
            SocialActionEntity socialAction = socialActionRepository.findById(request.socialAction());
            CompanyEntity company = companyRepository.findById((request.companyId().id()));
            OngEntity ong = ongRepository.findById((request.ong()));
            if (socialAction != null && socialAction != null && company != null && ong != null) {
                newContract.setSocialAction(socialAction);
                newContract.setCompany(company);
                newContract.setOng(ong);
            } else {
                throw new ObjectNotFoundException("Invalid ID in the request");
            }
            newContract.setProcessId(UUID.fromString(responseId));
            newContract.setDescription(request.description());
            newContract.setJustification(request.justification());
            newContract.setStatus(request.status());

            ContractEntity savedContract = repository.save(newContract);
            return savedContract;
        } else {
            throw new ObjectNotFoundException("Invalid request, could not create proccess");
        }
    }


    public Page<ContractEntity> findAll(Pageable paging, ContractParamsDto filters) {
        BooleanExpression predicate = contractPredicates.buildPredicate(filters);
        Page<ContractEntity> objects = repository.findAll(paging, predicate);
        return objects;
    }

    public ContractEntity findById(UUID id) {
        ContractEntity obj = repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }



    public ContractEntity update(UUID id, ContractUpdateDto dto, String token) {
        ContractEntity contract = repository.findById(id);
        if (contract == null){
            throw new ObjectNotFoundException("Contrato inexistente");
        }

        String requestUrlEndpoint = String.format("%s/processes/%s",pdtecUrl, contract.processId);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-Tenant", xTenant);
        Map<String, Object> makeBody = new HashMap<>();
        makeBody.put("status", contract.getStatus());
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
                HttpMethod.PATCH,
                requestPdtec,
                String.class
        );
        HttpStatusCode statusCode = responsePdtec.getStatusCode();
        int statusCodeValue = statusCode.value();

        if (statusCodeValue == 200){
            contract.setStatus(dto.status());
        }
        else{
            throw new RuntimeException("Not a valid StatusCode");
        }
        ContractEntity contractUpdated = repository.update(contract);
        return contract;
    }

    public void delete(UUID id) { repository.delete(id); }
}

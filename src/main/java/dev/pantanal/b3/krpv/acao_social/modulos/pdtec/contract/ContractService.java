package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract;

import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.request.ContractUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.contract.dto.response.ContractResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class ContractService {
    public ContractResponseDto create(ContractCreateDto request) {

    }

    public ResponseEntity<String> findAll(int page, int size) {
    }

    public ContractResponseDto findById(UUID contractId) {
    }

    public ResponseEntity<String> update(UUID id, ContractUpdateDto dto) {
    }

    public void delete(UUID id) {
    }
}

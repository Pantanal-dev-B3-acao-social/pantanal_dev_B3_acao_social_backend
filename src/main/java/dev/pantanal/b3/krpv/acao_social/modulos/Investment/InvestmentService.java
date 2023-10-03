package dev.pantanal.b3.krpv.acao_social.modulos.Investment;

import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository repository;

    public InvestmentEntity create(InvestmentCreateDto request) {
        InvestmentEntity newInvestiment = new InvestmentEntity();
        newInvestiment.setValue_money(request.value_money());
        newInvestiment.setDate(request.date());
        newInvestiment.setMotivation(request.motivation());
        newInvestiment.setApprovedAt(request.approvedAt());
        InvestmentEntity savedInvestment = repository.save(newInvestiment);
        return savedInvestment;
    }

    public InvestmentEntity findById(UUID id) {
        InvestmentEntity obj= repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

}

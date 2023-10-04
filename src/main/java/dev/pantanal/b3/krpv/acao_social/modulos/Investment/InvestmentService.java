package dev.pantanal.b3.krpv.acao_social.modulos.Investment;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.dto.request.InvestmentUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository.InvestmentPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository repository;
    @Autowired
    private InvestmentPredicates investmentPredicates;

    public InvestmentEntity create(InvestmentCreateDto request) {
        InvestmentEntity newInvestiment = new InvestmentEntity();
        newInvestiment.setValue_money(request.value_money());
        newInvestiment.setDate(request.date());
        newInvestiment.setMotivation(request.motivation());
        newInvestiment.setApprovedAt(request.approvedAt());
        InvestmentEntity savedInvestment = repository.save(newInvestiment);
        return savedInvestment;
    }

    public Page<InvestmentEntity> findAll(Pageable pageable, InvestmentParamsDto request) {
        BooleanExpression predicate = investmentPredicates.buildPredicate(request);
        Page<InvestmentEntity> objects = repository.findAll(pageable, predicate);
        return objects;
    }

    public InvestmentEntity findById(UUID id) {
        InvestmentEntity obj= repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }
    public InvestmentEntity update(UUID id, InvestmentUpdateDto request) {
        InvestmentEntity obj = repository.findById(id);
        if (obj == null){
            throw new ObjectNotFoundException("Resgistro não encontrado: " + id);
        }
        if (request.value_money() != null){
            obj.setValue_money(request.value_money());
        }
        if (request.date() != null){
            obj.setDate(request.date());
        }
        if (request.motivation() !=  null){
            obj.setMotivation(request.motivation());
        }
        if (request.approvedAt() != null){
            obj.setApprovedAt(request.approvedAt());
        }
        return repository.update(obj);
    }

    public void delete(UUID id) {repository.delete(id);}
}

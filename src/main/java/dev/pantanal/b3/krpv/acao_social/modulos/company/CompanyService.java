package dev.pantanal.b3.krpv.acao_social.modulos.company;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request.CompanyCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request.CompanyParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request.CompanyUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;
    @Autowired
    private CompanyPredicates companyPredicates;
    public CompanyEntity create(CompanyCreateDto request) {
        //verificar se precisa verificar cnpj já é registrado ou se o @CNPJ já resolve isso
        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(request.name());
        newCompany.setDescription(request.description());
        newCompany.setCnpj(request.cnpj());
        CompanyEntity savedCompany = repository.save(newCompany);
        return savedCompany;
    }

    public Page<CompanyEntity> findAll(Pageable paging, CompanyParamsDto filters) {
        BooleanExpression predicate = companyPredicates.buildPredicate(filters);
        Page<CompanyEntity> objects = repository.findAll(paging, predicate);
        // lançar exceções
        return objects;
    }

    public CompanyEntity findById(UUID id) {
        CompanyEntity obj = repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public CompanyEntity update(UUID id, CompanyUpdateDto request) {
        CompanyEntity obj = repository.findById(id);
        if (request.name() != null) {
            obj.setName(request.name());
        }
        if (request.description() != null) {
            obj.setDescription(request.description());
        }
        if (request.cnpj() != null) {
            obj.setCnpj(request.cnpj());
        }
        CompanyEntity updatedObj = repository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) { repository.delete(id); }


}


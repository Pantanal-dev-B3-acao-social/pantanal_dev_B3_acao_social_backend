package dev.pantanal.b3.krpv.acao_social.modulos.company;

import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.category.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request.CompanyCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;
    public CompanyEntity create(CompanyCreateDto request) {
        //verificar se precisa verificar cnpj já é registrado ou se o @CNPJ já resolve isso
        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(request.name());
        newCompany.setDescription(request.description());
        newCompany.setCnpj(request.cnpj());
        CompanyEntity savedCompany = repository.save(newCompany);
        return savedCompany;
    }

    public CompanyEntity findById(UUID id) {
        CompanyEntity obj = repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }
}

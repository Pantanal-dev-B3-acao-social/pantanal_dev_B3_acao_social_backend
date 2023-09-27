package dev.pantanal.b3.krpv.acao_social.modulos.company;

import dev.pantanal.b3.krpv.acao_social.modulos.company.dto.request.CompanyCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;
    public CompanyEntity create(CompanyCreateDto request) {
        CompanyEntity newCompany = new CompanyEntity();
        newCompany.setName(request.name());
        newCompany.setDescription(request.description());
        newCompany.setCnpj(request.cnpj());
        CompanyEntity savedCompany = repository.save(newCompany);
        return savedCompany;
    }
}

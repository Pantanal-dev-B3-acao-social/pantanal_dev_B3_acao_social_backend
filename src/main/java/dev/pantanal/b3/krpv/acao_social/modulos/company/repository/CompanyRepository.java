package dev.pantanal.b3.krpv.acao_social.modulos.company.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
@Repository
public class CompanyRepository {
    @Autowired
    private CompanyPostgresRepository companyPostgresRepository;
    public CompanyEntity save(CompanyEntity entityObj) {
        CompanyEntity companyEntity = companyPostgresRepository.save(entityObj);
        return companyEntity;

    }
}

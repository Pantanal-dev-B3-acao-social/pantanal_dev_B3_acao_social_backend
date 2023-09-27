package dev.pantanal.b3.krpv.acao_social.modulos.company.repository;

import com.github.javafaker.Company;
import dev.pantanal.b3.krpv.acao_social.modulos.category.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

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

    public CompanyEntity findById(UUID id) {
        CompanyEntity companyEntity = companyPostgresRepository.findById(id).orElse(null);
        return companyEntity;
    }
}

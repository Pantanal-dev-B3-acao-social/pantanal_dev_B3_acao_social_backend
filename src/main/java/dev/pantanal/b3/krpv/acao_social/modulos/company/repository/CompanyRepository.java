package dev.pantanal.b3.krpv.acao_social.modulos.company.repository;

import com.github.javafaker.Company;
import dev.pantanal.b3.krpv.acao_social.modulos.category.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class CompanyRepository {
    @Autowired
    private CompanyPostgresRepository companyPostgresRepository;
    @PersistenceContext
    private final EntityManager entityManager;
    public CompanyEntity save(CompanyEntity entityObj) {
        CompanyEntity companyEntity = companyPostgresRepository.save(entityObj);
        return companyEntity;

    }

    public CompanyEntity findById(UUID id) {
        CompanyEntity companyEntity = companyPostgresRepository.findById(id).orElse(null);
        return companyEntity;
    }
    @Transactional
    public CompanyEntity update(CompanyEntity obj) {
        CompanyEntity companyEntity = entityManager.merge(obj);
        return companyEntity;
    }

    public void delete(UUID id) {
        CompanyEntity companyEntity = companyPostgresRepository.findById(id).orElse(null);
        companyPostgresRepository.delete(companyEntity); //review null test case for object not found for deletion
    }
}

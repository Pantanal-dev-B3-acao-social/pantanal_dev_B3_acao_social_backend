package dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository;

import dev.pantanal.b3.krpv.acao_social.modulos.Investment.InvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.CategoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class InvestmentRepository {

    @Autowired
    private InvestmentPostgresRepository investmentPostgresRepository;
    @PersistenceContext
    private final EntityManager entityManager;
    public InvestmentEntity save(InvestmentEntity entityObj) {
        InvestmentEntity investmentEntity = investmentPostgresRepository.save(entityObj);
        return investmentEntity;
    }

    public InvestmentEntity findById(UUID id) {
        return investmentPostgresRepository.findById(id).orElse(null);
    }
}

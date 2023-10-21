package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.InvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.repository.InvestmentRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class InvestmentFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private InvestmentRepository repository;
    @Autowired
    SocialActionFactory socialActionFactory;
    @Autowired
    CompanyFactory companyFactory;
    @Autowired
    public InvestmentFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InvestmentEntity makeFakeEntity() {
        LocalDateTime date = LocalDateTime.now().plusHours(-9);
        String motivation = faker.lorem().sentence();
        LocalDateTime approvedAt = LocalDateTime.now();
        FindRegisterRandom findRegisterRandomSocial = new FindRegisterRandom<SocialActionEntity>(entityManager);
        List<SocialActionEntity> socialActions = findRegisterRandomSocial.execute("social_action", 1, SocialActionEntity.class);
        FindRegisterRandom findRandomCompany = new FindRegisterRandom<CompanyEntity>(entityManager);
        List<CompanyEntity> companies = findRandomCompany.execute("company", 1, CompanyEntity.class);
        InvestmentEntity entity = new InvestmentEntity();
        entity.setValueMoney(new BigDecimal(faker.number().numberBetween(1, 1000000)));
        entity.setDate(date);
        entity.setCompany(companies.get(0));
        entity.setMotivation(motivation);
        entity.setSocialAction(socialActions.get(0));
        entity.setApprovedDate(approvedAt);
        return entity;
    }

    public InvestmentEntity insertOne(InvestmentEntity toSave) {
        InvestmentEntity saved = repository.save(toSave);
        return saved;
    }

    public List<InvestmentEntity> insertMany(int amount) {
        List<InvestmentEntity> investments = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            InvestmentEntity investment = this.makeFakeEntity();
            investments.add(this.insertOne(investment));
        }
        return investments;
    }
}

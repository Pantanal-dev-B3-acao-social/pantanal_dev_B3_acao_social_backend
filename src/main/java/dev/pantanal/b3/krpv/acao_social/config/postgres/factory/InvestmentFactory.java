package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.InvestmentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.Investment.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
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
    private InvestmentRepository repository;

    @Autowired
    public InvestmentFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InvestmentEntity makeFakeEntity() {
        UUID createBy = UUID.randomUUID();
        UUID deleteBy = null;
        UUID lastModifiedBy = null;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        long value_money = faker.number().randomNumber();
        LocalDateTime date = LocalDateTime.now();
        String motivation = faker.lorem().sentence();
        LocalDateTime approvedAt = LocalDateTime.now();
        return new InvestmentEntity(
                1L,
                UUID.randomUUID(),
                value_money,
                date,
                motivation,
                approvedAt,
                createBy,
                lastModifiedBy,
                createdDate,
                lastModifiedDate,
                null,
                deleteBy
        );
    }

    public InvestmentEntity insertOne(InvestmentEntity toSave) {
        InvestmentEntity saved = repository.save(toSave);
        return saved;
    }

    public List<InvestmentEntity> insertMany(int amount) {
        List<InvestmentEntity> investments = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            InvestmentEntity socialActionEntity = this.makeFakeEntity();
            investments.add(this.insertOne(socialActionEntity));
        }
        return investments;
    }
}

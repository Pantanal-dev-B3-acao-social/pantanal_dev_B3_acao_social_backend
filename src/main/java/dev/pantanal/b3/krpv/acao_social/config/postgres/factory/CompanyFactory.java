package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.company.CompanyEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.company.repository.CompanyRepository;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCnpj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class CompanyFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private CompanyRepository repository;
    @Autowired
    GeneratorCnpj generatorCnpj;

    @Autowired
    public CompanyFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CompanyEntity makeFakeEntity() {
        UUID createBy = UUID.randomUUID();
        UUID deleteBy = null;
        UUID lastModifiedBy = null;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        String name = faker.name().fullName();
        String cnpj = generatorCnpj.cnpj(true);
        return new CompanyEntity(
                1L,
                UUID.randomUUID(),
                name,
                faker.lorem().sentence(),
                cnpj,
                createBy,
                lastModifiedBy,
                createdDate,
                lastModifiedDate,
                null,
                deleteBy
        );
    }

    public CompanyEntity insertOne(CompanyEntity toSave) {
        CompanyEntity saved = repository.save(toSave);
        return saved;
    }

    public List<CompanyEntity> insertMany(int amount) {
        List<CompanyEntity> socials = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            CompanyEntity companyEntity = this.makeFakeEntity();
            socials.add(this.insertOne(companyEntity));
        }
        return socials;
    }
}

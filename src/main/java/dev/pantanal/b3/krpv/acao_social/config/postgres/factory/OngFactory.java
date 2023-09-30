package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.OngEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.ong.repository.OngRepository;
import dev.pantanal.b3.krpv.acao_social.utils.FindRegisterRandom;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class OngFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private OngRepository ongRepository;
    @Autowired
    private GeneratorCode generatorCode;

    @Autowired
    public OngFactory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private FindRegisterRandom findRegisterRandom;

    public OngEntity makeFakeEntity() {
        UUID createBy = UUID.randomUUID();
        UUID deleteBy = null;
        UUID lastModifiedBy = null;
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = createdDate.plusHours(3).plusMinutes(30);
        String name = faker.name().fullName();
        String code = generatorCode.execute(name);
        String cnpj = "";
        UUID managerId = UUID.randomUUID();
        return new OngEntity(
                1L,
                UUID.randomUUID(),
                name,
                cnpj,
//                managerId,
                createBy,
                lastModifiedBy,
                createdDate,
                lastModifiedDate,
                null,
                deleteBy
        );
    }

    public OngEntity insertOne(OngEntity toSave) {
        OngEntity saved = ongRepository.save(toSave);
        return saved;
    }

    public List<OngEntity> insertMany(int amount) {
        List<OngEntity> ongs = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            OngEntity ongEntity = this.makeFakeEntity();
            ongs.add(this.insertOne(ongEntity));
        }
        return ongs;
    }
}

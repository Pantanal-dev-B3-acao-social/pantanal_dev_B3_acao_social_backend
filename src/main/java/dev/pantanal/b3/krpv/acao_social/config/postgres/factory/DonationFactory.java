package dev.pantanal.b3.krpv.acao_social.config.postgres.factory;

import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.repository.DonationRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.utils.EnumUtils;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCnpj;
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
public class DonationFactory {
    private static final Random random = new Random();
    private final Faker faker = new Faker();
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    GeneratorCnpj generatorCnpj;
    @Autowired
    PersonFactory donationFactory;

    @Autowired
    public DonationFactory(
            JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DonationEntity makeFakeEntity(SocialActionEntity social, PersonEntity donor, PersonEntity approved) {
        LocalDateTime donationDate = LocalDateTime.now().plusHours(-7).plusMinutes(40);
        LocalDateTime approvedDate = LocalDateTime.now().plusHours(-1);
        DonationEntity donationEntity = new DonationEntity();
        donationEntity.setSocialActionEntity(social);
        donationEntity.setDonatedByEntity(donor);
        donationEntity.setDonationDate(donationDate);
        donationEntity.setValueMoney(new BigDecimal(faker.number().numberBetween(1, 1000000)));
        donationEntity.setMotivation(faker.lorem().sentence());
        donationEntity.setApprovedBy(approved);
        donationEntity.setApprovedDate(approvedDate);
        return donationEntity;
    }

    public DonationEntity insertOne(DonationEntity toSave) {
        DonationEntity saved = donationRepository.save(toSave);
        return saved;
    }

    public List<DonationEntity> insertMany(int amount, SocialActionEntity social, PersonEntity donor, PersonEntity approved) {
        List<DonationEntity> entities = new ArrayList<>();
        for (int i=0; i<amount; i++) {
            DonationEntity entity = this.makeFakeEntity(social, donor, approved);
            entities.add(this.insertOne(entity));
        }
        return entities;
    }
    
}

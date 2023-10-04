package dev.pantanal.b3.krpv.acao_social.modulos.donation.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.repository.DonationPostgresRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Repository
public class DonationRepository {

    private final DonationPostgresRepository donationPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public DonationEntity findById(UUID id) {
        DonationEntity donationEntity = donationPostgresRepository.findById(id).orElse(null);
        return donationEntity;
    }

    public Page<DonationEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QDonationEntity qDonationEntity = QDonationEntity.donationEntity;

        List<DonationEntity> results = queryFactory.selectFrom(qDonationEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qDonationEntity)
                .from(qDonationEntity)
                .where(predicate)
                .fetch()
                .stream().count();

        return new PageImpl<>(results,pageable,total);
    }

    public DonationEntity save(DonationEntity obj) {
        DonationEntity donationEntity = donationPostgresRepository.save(obj);
        return donationEntity;
    }

    @Transactional
    public DonationEntity update(DonationEntity obj) {
        DonationEntity updatedEntity = entityManager.merge(obj);
        return updatedEntity;
    }

    public void delete(UUID id) {
        DonationEntity objEntity = findById(id);
        donationPostgresRepository.delete(objEntity);
    }
}

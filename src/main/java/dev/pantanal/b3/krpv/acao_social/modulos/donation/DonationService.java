package dev.pantanal.b3.krpv.acao_social.modulos.donation;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.DonationEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.repository.DonationPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.repository.DonationRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.dto.request.DonationUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.repository.DonationPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.donation.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public class DonationService {

    @Autowired
    private DonationRepository repository;
    @Autowired
    private DonationPredicates donationPredicates;
    public DonationEntity create(DonationCreateDto request) {
        DonationEntity newDonation = new DonationEntity();
        newDonation.setDonation_date(request.donation_date());
        newDonation.setValue_money(request.value_money());
        newDonation.setMotivation(request.motivation());
        DonationEntity savedDonation = repository.save(newDonation);
        return savedDonation;
    }

    public Page<DonationEntity> findAll(Pageable paging, DonationParamsDto filters) {
        BooleanExpression predicate = donationPredicates.buildPredicate(filters);
        Page<DonationEntity> objects = repository.findAll(paging, predicate);
        // lançar exceções
        return objects;
    }

    public DonationEntity findById(UUID id) {
        DonationEntity obj = repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public DonationEntity update(UUID id, DonationUpdateDto request) {
        DonationEntity obj = repository.findById(id);
        if (request.donation_date() != null) {
            obj.setDonation_date(request.donation_date());
        }
        if (request.value_money() != null) {
            obj.setValue_money(request.value_money());
        }
        if (request.motivation() != null) {
            obj.setMotivation(request.motivation());
        }
        DonationEntity updatedObj = repository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) { repository.delete(id); }

}

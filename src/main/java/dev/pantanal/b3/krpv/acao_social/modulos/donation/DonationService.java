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

@Service
public class DonationService {

    @Autowired
    private DonationRepository repository;
    @Autowired
    private DonationPredicates donationPredicates;

    public DonationEntity create(DonationCreateDto request) {
        DonationEntity newDonation = new DonationEntity();
        newDonation.setSocialActionEntity(request.socialActionEntity());
        newDonation.setDonatedByEntity(request.donatedByEntity());
        newDonation.setDonationDate(request.donationDate());
        newDonation.setValueMoney(request.valueMoney());
        newDonation.setMotivation(request.motivation());
        newDonation.setApprovedBy(request.approvedBy());
        newDonation.setApprovedDate(request.approvedDate());
        DonationEntity savedDonation = repository.save(newDonation);
        return savedDonation;
    }

    public Page<DonationEntity> findAll(Pageable paging, DonationParamsDto filters) {
        BooleanExpression predicate = donationPredicates.buildPredicate(filters);
        Page<DonationEntity> objects = repository.findAll(paging, predicate);
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
        if (request.socialActionEntity() != null) {
            obj.setSocialActionEntity(request.socialActionEntity());
        }
        if (request.donatedByEntity() != null) {
            obj.setDonatedByEntity(request.donatedByEntity());
        }
        if (request.donationDate() != null) {
            obj.setDonationDate(request.donationDate());
        }
        if (request.valueMoney() != null) {
            obj.setValueMoney(request.valueMoney());
        }
        if (request.motivation() != null) {
            obj.setMotivation(request.motivation());
        }
        if (request.approvedBy() != null) {
            obj.setApprovedBy(request.approvedBy());
        }
        if (request.approvedDate() != null) {
            obj.setApprovedDate(request.approvedDate());
        }
        DonationEntity updatedObj = repository.update(obj);
        return updatedObj;
    }

    public void delete(UUID id) { repository.delete(id); }

}
package dev.pantanal.b3.krpv.acao_social.modulos.interest;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.request.InterestCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.dto.request.InterestParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.repository.InterestPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.repository.InterestRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InterestService {

    @Autowired
    private InterestRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InterestPredicates donationPredicates;

    public InterestEntity create(InterestCreateDto request) {
        InterestEntity newInterest = new InterestEntity();
        CategoryEntity category = categoryRepository.findById(request.category());
        PersonEntity personEntity = personRepository.findById(request.person());

        if (category != null && category != null && personEntity !=null){
            newInterest.setCategory(category);
            newInterest.setPerson(personEntity);
        }
        else{
            throw new ObjectNotFoundException("Element of ID invalid");
        }
        InterestEntity savedInterest = repository.save(newInterest);
        return savedInterest;
    }

    public Page<InterestEntity> findAll(Pageable paging, InterestParamsDto filters) {
        BooleanExpression predicate = donationPredicates.buildPredicate(filters);
        Page<InterestEntity> objects = repository.findAll(paging, predicate);
        return objects;
    }

    public InterestEntity findById(UUID id) {
        InterestEntity obj = repository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public void delete(UUID id) { repository.delete(id); }

}

package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategorySocialActionTypePostgresRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionPredicates;
import org.springframework.stereotype.Service;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.dto.request.SocialActionUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.repository.SocialActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SocialActionService {

    @Autowired
    private SocialActionRepository socialActionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SocialActionPredicates socialActionPredicates;
    @Autowired
    private CategorySocialActionTypePostgresRepository categorySocialActionTypePostgresRepository;

    public SocialActionEntity create(SocialActionCreateDto dataRequest) {
        SocialActionEntity toSave = new SocialActionEntity();
        toSave.setName(dataRequest.name());
        toSave.setDescription(dataRequest.description());
        SocialActionEntity socialActionSaved = socialActionRepository.save(toSave);
        for (UUID categoryId : dataRequest.categoryTypeIds()) {
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId);
            if (categoryEntity != null) {
                CategorySocialActionTypeEntity typeCategory = new CategorySocialActionTypeEntity();
                typeCategory.setCategoryEntity(categoryEntity);
                typeCategory.setSocialActionEntity(socialActionSaved);
                categorySocialActionTypePostgresRepository.save(typeCategory);
//                typeCategory.setSocialActionEntity(toSave);
                socialActionSaved.getCategorySocialActionTypeEntities().add(typeCategory);
            }
        }
        return socialActionSaved;
    }

    public void delete(UUID id) {
        socialActionRepository.delete(id);
    }

    public Page<SocialActionEntity> findAll(Pageable pageable, SocialActionParamsDto filters) {
        BooleanExpression predicate = socialActionPredicates.buildPredicate(filters);
        Page<SocialActionEntity> objects = socialActionRepository.findAll(pageable, predicate);
        // lançar exceções
        return objects;
    }

    public SocialActionEntity findById(UUID id) {
        SocialActionEntity obj = socialActionRepository.findByIdWithCategories(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public SocialActionEntity update(UUID id, SocialActionUpdateDto request){
        SocialActionEntity obj = socialActionRepository.findById(id);
        if (request.name() != null){
            obj.setName(request.name());
        }
        if (request.description() != null){
            obj.setDescription(request.description());
        }
        if (request.categoryTypeIds() != null) {
            List<CategorySocialActionTypeEntity> categorySocialActionTypeEntityList = new ArrayList<>();
            for (UUID categoryId : request.categoryTypeIds()) {
                CategoryEntity categoryEntity = categoryRepository.findById(categoryId);
                if (categoryEntity != null) {
                    CategorySocialActionTypeEntity typeCategory = new CategorySocialActionTypeEntity();
                    typeCategory.setCategoryEntity(categoryEntity);
                    typeCategory.setSocialActionEntity(obj);
                    CategorySocialActionTypeEntity categorySocialActionTypeEntity = categorySocialActionTypePostgresRepository.save(typeCategory);
                    categorySocialActionTypeEntityList.add(categorySocialActionTypeEntity);
                }

            }
            // Obtenha a lista existente atual de CategorySocialActionTypeEntity
            List<CategorySocialActionTypeEntity> existingCategories = obj.getCategorySocialActionTypeEntities();
            // Remova os itens antigos que não estão na nova lista
            existingCategories.removeIf(existingCategory -> !categorySocialActionTypeEntityList.contains(existingCategory));
            // Adicione os novos itens à lista existente
            existingCategories.addAll(categorySocialActionTypeEntityList);
            // Atualize a lista de CategorySocialActionTypeEntity na entidade SocialActionEntity
            obj.setCategorySocialActionTypeEntities(existingCategories);
            // TODO: estou atualizando, preciso add novo, e remover os antigos
//            obj.setCategorySocialActionTypeEntities(categorySocialActionTypeEntityList);
        }
        SocialActionEntity updatedObj = socialActionRepository.update(obj);
        return updatedObj;
    }

}

package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request.CategoryGroupCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request.CategoryGroupParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request.CategoryGroupUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository.CategoryGroupPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository.CategoryGroupRepository;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CategoryGroupService {

    @Autowired
    private CategoryGroupRepository categoryGroupRepository;
    @Autowired
    private CategoryGroupPredicates categoryGroupPredicates;
    @Autowired
    private GeneratorCode generatorCode;

    public CategoryGroupEntity create(CategoryGroupCreateDto dataRequest) {
        CategoryGroupEntity entity = new CategoryGroupEntity();
        if (dataRequest.parentCategoryGroup() != null){
            CategoryGroupEntity fatherEntity = categoryGroupRepository.findById(dataRequest.parentCategoryGroup());
            entity.setParentCategoryGroupEntity(fatherEntity);
        }
        entity.setName(dataRequest.name());
        entity.setDescription(dataRequest.description());
        String code = generatorCode.execute(entity.getName());
        entity.setCode(code);
        entity.setVisibility(dataRequest.visibility());
        CategoryGroupEntity savedObj = categoryGroupRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        categoryGroupRepository.delete(id);
    }

    public Page<CategoryGroupEntity> findAll(Pageable pageable, CategoryGroupParamsDto filters) {
        BooleanExpression predicate = categoryGroupPredicates.buildPredicate(filters);
        Page<CategoryGroupEntity> objects = categoryGroupRepository.findAll(pageable, predicate);
        return objects;
    }

    public CategoryGroupEntity findById(UUID id) {
        CategoryGroupEntity obj = categoryGroupRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public CategoryGroupEntity update(UUID id, CategoryGroupUpdateDto request){
        CategoryGroupEntity obj = categoryGroupRepository.findById(id);

        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        if (request.name() != null) {
            obj.setName(request.name());
            String code = generatorCode.execute(request.name());
            obj.setCode(code);
        }
        if (request.description() != null) {
            obj.setDescription(request.description());
        }
        if (request.visibility() != null) {
            obj.setVisibility(request.visibility());
        }
        CategoryGroupEntity parent = null;
        if (request.parentCategoryGroup() != null) {
            parent = categoryGroupRepository.findById(request.parentCategoryGroup());
            if (parent == null) {
                throw new ObjectNotFoundException("Registro não encontrado: " + request.parentCategoryGroup());
            }
        }
        obj.setParentCategoryGroupEntity(parent);
        CategoryGroupEntity updatedObj = categoryGroupRepository.update(obj);
        return updatedObj;
    }

}

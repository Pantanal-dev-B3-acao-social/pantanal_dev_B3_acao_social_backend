package dev.pantanal.b3.krpv.acao_social.modulos.category;

import com.querydsl.core.types.dsl.BooleanExpression;
import dev.pantanal.b3.krpv.acao_social.exception.ObjectNotFoundException;
import dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request.CategoryCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request.CategoryParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.dto.request.CategoryUpdateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategoryEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.CategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository.CategoryGroupRepository;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryPredicates;
import dev.pantanal.b3.krpv.acao_social.modulos.category.repository.CategoryRepository;
import dev.pantanal.b3.krpv.acao_social.utils.GeneratorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryGroupRepository categoryGroupRepository;
    @Autowired
    private CategoryPredicates categoryPredicates;
    @Autowired
    private GeneratorCode generatorCode;

    public CategoryEntity create(CategoryCreateDto dataRequest) {
        CategoryGroupEntity categoryGroup = categoryGroupRepository.findById(dataRequest.categoryGroup());
        if (categoryGroup == null) {
            throw new ObjectNotFoundException("Invalid element ID");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryGroup(categoryGroup);
        entity.setName(dataRequest.name());
        entity.setDescription(dataRequest.description());
        String code = generatorCode.execute(entity.getName());
        entity.setCode(code);
        entity.setVisibility(dataRequest.visibility());
        CategoryEntity savedObj = categoryRepository.save(entity);
        return savedObj;
    }

    public void delete(UUID id) {
        categoryRepository.delete(id);
    }

    public Page<CategoryEntity> findAll(Pageable pageable, CategoryParamsDto filters) {
        BooleanExpression predicate = categoryPredicates.buildPredicate(filters);
        Page<CategoryEntity> objects = categoryRepository.findAll(pageable, predicate);
        // lançar exceções
        return objects;
    }

    public CategoryEntity findById(UUID id) {
        CategoryEntity obj = categoryRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        return obj;
    }

    public CategoryEntity update(UUID id, CategoryUpdateDto request){
        CategoryEntity obj = categoryRepository.findById(id);
        if (obj == null) {
            throw new ObjectNotFoundException("Registro não encontrado: " + id);
        }
        if (request.name() != null) {
            obj.setName(request.name());
        }
        if (request.description() != null) {
            obj.setDescription(request.description());
        }
        if (request.visibility() != null) {
            obj.setVisibility(request.visibility());
        }
        if (request.categoryGroup() != null) {
            CategoryGroupEntity groupEntity = categoryGroupRepository.findById(request.categoryGroup());
            if (groupEntity == null) {
                throw new ObjectNotFoundException("Registro não encontrado: " + request.categoryGroup());
            }
            obj.setCategoryGroup(groupEntity);
        }
        CategoryEntity updatedObj = categoryRepository.update(obj);
        return updatedObj;
    }

}

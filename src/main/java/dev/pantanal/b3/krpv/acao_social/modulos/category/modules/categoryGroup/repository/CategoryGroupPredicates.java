package dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.repository;

import com.querydsl.core.types.dsl.*;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.QCategoryGroupEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.request.CategoryGroupParamsDto;
import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.enums.VisibilityCategoryGroupEnum;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CategoryGroupPredicates {

    public BooleanExpression buildPredicate(CategoryGroupParamsDto filters){

        QCategoryGroupEntity q = QCategoryGroupEntity.categoryGroupEntity;
        BooleanExpression predicate = Expressions.asBoolean(true).isTrue();

        if (filters.name() != null) {
            StringPath filterPath = q.name;
            predicate = predicate.and(filterPath.like(filters.name()+ "%"));
        }
        if (filters.description() != null) {
            StringPath filterPath = q.description;
            predicate = predicate.and(filterPath.eq(filters.description()));
        }
        if (filters.code() != null) {
            StringPath filterPath = q.code;
            predicate = predicate.and(filterPath.eq(filters.code()));
        }
        if (filters.parentCategoryGroup() != null) {
            predicate = predicate.and(q.parentCategoryGroupEntity.id.in(filters.parentCategoryGroup()));
        }
        if (filters.visibility() != null) {
            EnumPath<VisibilityCategoryGroupEnum> filterPath = q.visibility;
            predicate = predicate.and(filterPath.eq(filters.visibility()));
        }
        if (filters.lastModifiedBy() != null) {
            ComparablePath<UUID> filterPath = q.lastModifiedBy;
            predicate = predicate.and(filterPath.eq(filters.lastModifiedBy()));
        }
        if (filters.createdDate() != null) {
            DateTimePath<LocalDateTime> filterPath = q.createdDate;
            predicate = predicate.and(filterPath.eq(filters.createdDate()));
        }
        if (filters.lastModifiedDate() != null) {
            DateTimePath<LocalDateTime> filterPath = q.lastModifiedDate;
            predicate = predicate.and(filterPath.eq(filters.lastModifiedDate()));
        }
        if (filters.deletedDate() != null) {
            DateTimePath<LocalDateTime> filterPath = q.deletedDate;
            predicate = predicate.and(filterPath.eq(filters.deletedDate()));
        }
        if (filters.deletedBy() != null) {
            ComparablePath<UUID> filterPath = q.deletedBy;
            predicate = predicate.and(filterPath.eq(filters.deletedBy()));
        }
        return predicate;
    }

}

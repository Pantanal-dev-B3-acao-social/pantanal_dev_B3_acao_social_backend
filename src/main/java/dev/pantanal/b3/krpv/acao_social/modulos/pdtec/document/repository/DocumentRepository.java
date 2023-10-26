package dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;


import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.DocumentEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.pdtec.document.QDocumentEntity;
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
public class DocumentRepository {

    private final DocumentPostgresRepository documentPostgresRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public DocumentEntity save(DocumentEntity obj) {
        DocumentEntity documentEntity = documentPostgresRepository.save(obj);
        return documentEntity;
    }

    public DocumentEntity findById(UUID id) {
        DocumentEntity documentEntity = documentPostgresRepository.findById(id).orElse(null);
        return documentEntity;
    }

    public Page<DocumentEntity> findAll(Pageable pageable, BooleanExpression predicate) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QDocumentEntity qDocumentEntity = QDocumentEntity.documentEntity;
        List<DocumentEntity> results = queryFactory.selectFrom(qDocumentEntity)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.query()
                .select(qDocumentEntity)
                .from(qDocumentEntity)
                .where(predicate)
                .fetch()
                .stream().count();

        return new PageImpl<>(results,pageable,total);
    }

    @Transactional
    public DocumentEntity update(DocumentEntity toUpdate){
        DocumentEntity documentUpdated = entityManager.merge(toUpdate);
        entityManager.flush();
        return documentUpdated;
    }

    public void delete(UUID id) {
        documentPostgresRepository.deleteById(id);
    }
}

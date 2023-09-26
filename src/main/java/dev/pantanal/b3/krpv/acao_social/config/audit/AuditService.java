package dev.pantanal.b3.krpv.acao_social.config.audit;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuditService {

    @Autowired
    private EntityManager entityManager;

    /**
     * recuperar uma lista de números de revisão para uma entidade específica com base em seu ID.
     * Isso permite que você saiba quais revisões estão associadas a uma entidade.
     */
    public List<Number> getEntityReviews(Class<?> entity, UUID id) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader.getRevisions(entity, id);
    }

    /**
     * Recuperar Dados de Auditoria para uma Entidade em uma Revisão Específica
     */
    public Object GetAuditData(Class<?> entity, UUID id, Number revisao) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        return auditReader.find(entity, id, revisao);
    }

}

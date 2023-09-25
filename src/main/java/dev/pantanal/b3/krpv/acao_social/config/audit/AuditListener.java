package dev.pantanal.b3.krpv.acao_social.config.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class AuditListener {
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyOperation(Object object) {
        // TODO:
    }

}

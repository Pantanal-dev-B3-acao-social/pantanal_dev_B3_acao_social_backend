package dev.pantanal.b3.krpv.acao_social.modulos.pcd;

import dev.pantanal.b3.krpv.acao_social.config.audit.AuditListener;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name="pcd")
@Entity(name="Pcd")
@EntityListeners(AuditListener.class)
//@AuditTable("z_aud_pcd")
//@Audited
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE pcd SET deleted_date = CURRENT_DATE WHERE id=? and version=?")
//@Where(clause = "deleted_date IS NOT NULL")
public class PcdEntity {

    @Valid
    @Version
    private Long version = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    private UUID id;

    @Column(name = "name", nullable = false)

    private String name;

    @Column(name = "observation", nullable = false)
    private String observation;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "acronym", nullable = false)
    private String acronym;

    @CreatedBy
    @Column(name = "created_by")
    private UUID createdBy; // TODO: UUID

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private UUID lastModifiedBy; // TODO: UUID

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "deleted_by")
    private UUID deletedBy; // TODO: UUID


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            this.createdBy = UUID.fromString(userId);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            this.lastModifiedBy = UUID.fromString(userId);
        }
    }
}


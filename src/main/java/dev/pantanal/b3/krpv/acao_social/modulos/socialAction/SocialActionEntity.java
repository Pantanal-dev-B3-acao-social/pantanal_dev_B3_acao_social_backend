package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import dev.pantanal.b3.krpv.acao_social.config.audit.AuditListener;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Table(name="social_action")
@Entity(name="SocialAction")
@AuditTable("z_aud_social_action")
@EntityListeners(AuditListener.class)
@Audited
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SocialActionEntity {

    @Valid
    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    private UUID id;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String description;
    //@Column
    //@NotBlank
    //private String organizer;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "deleted_by")
    private String deletedBy;


    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            this.createdBy = userId;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            this.lastModifiedBy = userId;
        }
    }

    @PreRemove
    protected void onRemove() {
        this.deletedDate = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            this.deletedBy = userId;
        }
    }

}

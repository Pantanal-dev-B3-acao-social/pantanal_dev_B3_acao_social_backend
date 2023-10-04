package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import dev.pantanal.b3.krpv.acao_social.config.audit.AuditListener;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.session.SessionEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name="social_action")
@Entity(name="SocialAction")
@EntityListeners(AuditListener.class)
//@AuditTable("z_aud_social_action")
//@Audited
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SocialActionEntity {

    @Valid
    @Version
    private Long version = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    private UUID id;
    @Column(nullable = false)
//    @NotBlank
    private String name;
    @Column(nullable = false)
//    @NotBlank
    private String description;

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

    /**
     * mappedBy: o nome do atributo na tabela CategorySocialActionTypeEntity que faz referencia a esta tabela
     * cascade = CascadeType.PERSIST faz com que se salvar SocialActionEntity passando uma entidade de CategorySocialActionTypeEntity ambas serão salvas juntas
     * orphanRemoval = true faz com que SocialActionEntity é optional ter preenchido alguma CategorySocialActionTypeEntity
     */
//    @Optional
    @OneToMany(mappedBy = "socialActionEntity", orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CategorySocialActionTypeEntity> categorySocialActionTypeEntities = new ArrayList<>();

    @OneToMany(mappedBy = "socialAction", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<SessionEntity> sessionsEntities;

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

    @PreRemove
    protected void onRemove() {
        this.deletedDate = LocalDateTime.now();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            this.deletedBy = UUID.fromString(userId);
        }
    }

}

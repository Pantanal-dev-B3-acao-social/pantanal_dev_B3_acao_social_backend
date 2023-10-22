package dev.pantanal.b3.krpv.acao_social.modulos.voluntary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.pantanal.b3.krpv.acao_social.config.audit.AuditListener;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.enums.StatusEnum;
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

@Table(name="voluntary")
@Entity(name="Voluntary")
@EntityListeners(AuditListener.class)
//@AuditTable("z_aud_voluntary")
//@Audited
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE voluntary SET deleted_date=CURRENT_DATE WHERE id=? and version=?")
public class VoluntaryEntity {

    @Valid
    @Version
    private Long version = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

    @Column(nullable = true)
    private String observation;

    @ManyToOne
    @JoinColumn(name= "social_action_id", nullable = false)
    @JsonBackReference
    private SocialActionEntity socialAction;

    @ManyToOne
    @JoinColumn(name= "person_id", nullable = false)
    @JsonManagedReference
    private PersonEntity person;

    @ManyToOne
    @JoinColumn(name = "approved_by", nullable = true)
    private PersonEntity approvedBy;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    private StatusEnum status;

    @Column(name = "feedback_score_voluntary")
    private Integer feedbackScoreVoluntary;

    @Column(name = "feedback_voluntary")
    private String feedbackVoluntary;

    @CreatedBy
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private UUID lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "deleted_by")
    private UUID deletedBy;


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

package dev.pantanal.b3.krpv.acao_social.modulos.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.pantanal.b3.krpv.acao_social.config.audit.AuditListener;
import dev.pantanal.b3.krpv.acao_social.modulos.interest.InterestEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.presence.QPresenceEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.voluntary.VoluntaryEntity;
import jakarta.persistence.*;
//import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name="person")
@Entity(name="Person")
@EntityListeners(AuditListener.class)
//@AuditTable("z_aud_person")
//@Audited
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE person SET deleted_date = CURRENT_DATE where id=? and version=?")
public class PersonEntity {

//    @Valid
    @Version
    private Long version = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

//    @CreatedBy
    @Column(name = "user_by", nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(name = "date_birth")
    private LocalDateTime dateBirth;

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    private StatusEnum status;

    @Column(nullable = false, unique = true)
//    @NotBlank
//    @CPF
    String cpf;

    @Column(name = "engagement_score", nullable = true)
    Integer engagementScore = 0;

    @CreatedBy
    @Column(name = "created_by")
    private UUID createdBy;

    @CreatedDate
    @Column(name = "created_date")
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

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private List<VoluntaryEntity> voluntaryEntities;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private List<PresenceEntity> presenceEntities;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
    private List<InterestEntity> interestEntities;

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

package dev.pantanal.b3.krpv.acao_social.modulos.donation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.pantanal.b3.krpv.acao_social.config.audit.AuditListener;
import dev.pantanal.b3.krpv.acao_social.modulos.category.entity.CategorySocialActionTypeEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name="donation")
@Entity(name="Donation")
//@AuditTable("z_aud_donation")
@EntityListeners(AuditListener.class)
//@Audited
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE donation SET deleted_date = CURRENT_DATE WHERE id=? and version=?")
public class DonationEntity {

    @Valid
    @Version
    private Long version = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

    @ManyToOne
    @JoinColumn(name = "social_action_id")
    @ToString.Exclude
    private SocialActionEntity socialActionEntity;

    @ManyToOne
    @JoinColumn(name = "donated_by")
    @ToString.Exclude
    private PersonEntity donatedByEntity;

    @Column(nullable = false, name = "donation_date")
    private LocalDateTime donationDate;

    @Column(nullable = false, name = "value_money")
//    @NotBlank
    private BigDecimal valueMoney;

    @Column(nullable = false)
//    @NotBlank
    String motivation;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    @ToString.Exclude
    private PersonEntity approvedBy;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @CreatedBy
    @Column(name = "created_by")
    private UUID createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private UUID lastModifiedBy;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

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

//    @PreRemove
//    protected void onRemove() {
//        this.deletedDate = LocalDateTime.now();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String userId = authentication.getName();
//            this.deletedBy = UUID.fromString(userId);
//        }
//    }

    // TODO: implementar categorias da doação
    //    @OneToMany(mappedBy = "socialActionEntity", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //    @ToString.Exclude
    //    @JsonIgnoreProperties("category_donation_id")
    //    private List<CategoryDonationTypeEntity> categoryDonationTypeEntities = new ArrayList<>();

}

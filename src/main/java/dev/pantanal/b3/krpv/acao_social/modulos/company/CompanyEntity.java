package dev.pantanal.b3.krpv.acao_social.modulos.company;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.pantanal.b3.krpv.acao_social.config.audit.AuditListener;
import dev.pantanal.b3.krpv.acao_social.modulos.investment.InvestmentEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name="company")
@Entity(name="Company")
//@AuditTable("z_aud_company")
@EntityListeners(AuditListener.class)
//@Audited
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE company SET deleted_date = CURRENT_DATE WHERE id=? and version=?")
public class CompanyEntity {

    @Valid
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    @NotNull
    UUID id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String description;


    @Column(nullable = false, unique = true)
    @NotBlank
    @CNPJ
    String cnpj;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedBy
    private UUID lastModifiedBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "deleted_by")
    private UUID deletedBy;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonBackReference
    private List<InvestmentEntity> investment;

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

}

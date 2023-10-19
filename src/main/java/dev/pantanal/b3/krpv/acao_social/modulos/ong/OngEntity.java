package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import dev.pantanal.b3.krpv.acao_social.modulos.ong.enums.StatusEnum;
import dev.pantanal.b3.krpv.acao_social.modulos.person.PersonEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name="ong")
@Entity(name="Ong")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@SQLDelete(sql = "UPDATE ong SET deleted_date = CURRENT_DATE WHERE id=? and version=?")
public class OngEntity {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank
    @CNPJ
    String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(length = 255)
    StatusEnum status;

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

    // TODO: criar ManyToMany responsaveis
    @NotNull
    @ManyToOne
    @JoinColumn(name = "responsibility_id")
    @ToString.Exclude
    private PersonEntity responsibleEntity;

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

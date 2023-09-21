package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;
// TODO: import org.hibernate.envers.Audited;

@Table(name="social_action")
@Entity(name="socialAction")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
// TODO: @Audited
public class SocialActionEntity {

    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(nullable = false)
    @NotNull
    private String name;
    @Column(nullable = false)
    @NotNull
    private String description;
    @Column
    @NotNull
    private String organizer;


}

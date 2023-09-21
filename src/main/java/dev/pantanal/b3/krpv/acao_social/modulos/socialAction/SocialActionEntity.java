package dev.pantanal.b3.krpv.acao_social.modulos.socialAction;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
// TODO: import org.hibernate.envers.Audited;

@Table(name="social_action")
@Entity(name="SocialAction")
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
    private String name;
    @Column(nullable = false)
    private String description;

}

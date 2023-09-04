package dev.pantanal.b3.krpv.acao_social.entity;

import jakarta.persistence.*;
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
    private String name;
    private String description;

}

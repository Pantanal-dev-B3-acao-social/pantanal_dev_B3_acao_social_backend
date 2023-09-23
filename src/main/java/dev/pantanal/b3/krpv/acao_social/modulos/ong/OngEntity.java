package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Table(name="ong")
@Entity(name="Ong")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OngEntity {
    @Version
    private Long version;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;
    @Column(nullable = false)
    private String name;
}

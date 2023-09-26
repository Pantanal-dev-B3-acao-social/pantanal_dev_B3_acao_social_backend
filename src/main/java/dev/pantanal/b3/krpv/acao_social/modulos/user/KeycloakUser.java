package dev.pantanal.b3.krpv.acao_social.modulos.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;

/*
* representar os dados do usuário que deseja recuperar.
*/
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) // Ignorar campos desconhecidos
public class KeycloakUser {
    private String id;
    private String username;
    private Boolean enabled;
    private Boolean totp;
    private Boolean emailVerified;
    private String firstName;
    private String lastName;
    private Map<String, List<String>> attributes; // Mapear atributos personalizados do usuário
    private String email;
    private String self;
    private String origin;
    private Long createdTimestamp;
    private String federationLink;
    private String serviceAccountClientId;
    private List<String> disableableCredentialTypes;
}

package dev.pantanal.b3.krpv.acao_social.utils;

import com.nimbusds.jwt.*;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.jwt.Jwt;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

@Component
public class LoginMock {

    @Autowired
    private KeyclockAuthService keyclockAuthService;
    @Autowired
    private GenerateTokenUserForLogged generateTokenUserLoggedMock;

    public void authenticateWithToken(String jwtToken) {
        JWTClaimsSet claim = decodeJwtToken(jwtToken);
//        String jwtTokenString = claim.toString();
        Jwt jwt = Jwt.withTokenValue(jwtToken)
                .header("alg", "RS256")
                .header("typ", "JWT")
                .claim("yourClaimName", claim) // TODO: keyclock ?
                .build();
        Collection<? extends GrantedAuthority> grants = null;
        String userId = extractUserIdFromJwt(jwtToken);
        // Crie um objeto JwtAuthenticationToken com base no token JWT
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt, grants, userId);
        // Configure a autenticação no contexto do Spring Security
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    }


    private JWTClaimsSet decodeJwtToken(String jwtToken) {
        try {
            JWT jwt = JWTParser.parse(jwtToken);

            // Verifique o tipo de token (SignedJWT, PlainJWT, etc.) e atue conforme necessário
            if (jwt instanceof SignedJWT) {
                SignedJWT signedJWT = (SignedJWT) jwt;
                // Você pode acessar as informações do JWT assim:
                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                return claimsSet;
            } else if (jwt instanceof PlainJWT) {
                PlainJWT plainJWT = (PlainJWT) jwt;
                // Se for um Plain JWT (não assinado), você pode acessar as informações diretamente:
                Map<String, Object> claims = plainJWT.getPayload().toJSONObject();
                // claims contém as informações do JWT
            } else {
                throw new RuntimeException("Tipo de token JWT não suportado: " + jwt.getClass().getName());
            }
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao decodificar o token JWT", e);
        }
        return null;
    }

    public String extractUserIdFromJwt(String jwtToken) {
        try {
            JWT jwt = JWTParser.parse(jwtToken);
            // Verifique o tipo de token (SignedJWT, PlainJWT, etc.) e atue conforme necessário
            if (jwt instanceof SignedJWT) {
                SignedJWT signedJWT = (SignedJWT) jwt;
                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                return claimsSet.getSubject(); // O campo "sub" contém o ID do usuário
            } else if (jwt instanceof PlainJWT) {
                PlainJWT plainJWT = (PlainJWT) jwt;
                // Se for um Plain JWT (não assinado), você pode acessar as informações diretamente:
                Map<String, Object> claims = plainJWT.getPayload().toJSONObject();
                return (String) claims.get("sub"); // O campo "sub" contém o ID do usuário
            } else {
                throw new RuntimeException("Tipo de token JWT não suportado: " + jwt.getClass().getName());
            }
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao decodificar o token JWT", e);
        }
    }

}

package dev.pantanal.b3.krpv.acao_social.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;

public class JWTConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    /**
     * esta função é responsavel por extrair do payload os cargos deste token do user
     * ele sera usado no controller na notação: @PreAuthorize("hasAnyRole('SOCIAL_ACTION_CREATE')")
     * sera usado em DefaultSecurityConfig SecurityFilterChain oauth2ResourceServer
     */
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
        Collection<String> roles = realmAccess.get("roles");
        var grants = roles.stream().map(
                role -> new SimpleGrantedAuthority("ROLE_" + role)
        ).toList();
        return new JwtAuthenticationToken(jwt, grants);
    }

}

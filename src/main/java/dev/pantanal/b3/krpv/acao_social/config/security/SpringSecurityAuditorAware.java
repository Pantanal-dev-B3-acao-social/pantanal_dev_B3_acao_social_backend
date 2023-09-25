package dev.pantanal.b3.krpv.acao_social.config.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    /*
    * Obtém o ID do usuário autenticado (assumindo que o ID é uma string)
    */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            return Optional.of(userId);
        }
        return Optional.empty();
    }

}

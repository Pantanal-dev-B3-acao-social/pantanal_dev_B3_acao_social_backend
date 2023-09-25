package dev.pantanal.b3.krpv.acao_social.config.web;

import dev.pantanal.b3.krpv.acao_social.config.audit.CorrelationIdInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /* adiciona o interceptor */
        registry.addInterceptor(new CorrelationIdInterceptor());
    }
}

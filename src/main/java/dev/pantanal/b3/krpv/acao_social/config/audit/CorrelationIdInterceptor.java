package dev.pantanal.b3.krpv.acao_social.config.audit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Component
public class CorrelationIdInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(CorrelationIdInterceptor.class);
    private static String CORRELATION_ID_NAME = "correlationId";
    private static String USER_LOGGED_ID = "userLoggedId";
    private static String CONTROLLER_ACTION = "controllerAction";
    private String correlationId;
    private String userLoggedId;
    private String controllerAndAction;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        correlationId = getCorrelationIdHeader(request);
        userLoggedId = getUserLogged();
        controllerAndAction = getControlerAndAction(handler);
        MDC.put(CORRELATION_ID_NAME, correlationId);
        MDC.put(USER_LOGGED_ID, userLoggedId);
        MDC.put(CONTROLLER_ACTION, controllerAndAction);
        logger.info("correlation ID: " + correlationId);
        return true;
    }

    private String getCorrelationIdHeader(HttpServletRequest request) {
        correlationId = request.getHeader("correlation-id");
        if(this.correlationId == null || correlationId.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        return correlationId;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler, Exception ex
    ) throws Exception {
        MDC.remove(CORRELATION_ID_NAME);
    }

    public String getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return username;
    }

    public String getControlerAndAction(Object handler) {
        String controllerName = "";
        String methodName = "";
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> controllerClass = handlerMethod.getBeanType();
            controllerName = controllerClass.getName();
            methodName = handlerMethod.getMethod().getName();
        }
        return controllerName + "." + methodName;
    }

}

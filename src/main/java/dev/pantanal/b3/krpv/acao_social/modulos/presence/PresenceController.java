package dev.pantanal.b3.krpv.acao_social.modulos.presence;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static dev.pantanal.b3.krpv.acao_social.modulos.presence.PresenceController.ROUTE_PRESENCE;

@RestController
@RequestMapping(ROUTE_PRESENCE)
public class PresenceController {

    public static final String ROUTE_PRESENCE = "/v1/presence";
}

package dev.pantanal.b3.krpv.acao_social.modulos.ong;

import dev.pantanal.b3.krpv.acao_social.modulos.socialAction.SocialActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static dev.pantanal.b3.krpv.acao_social.modulos.ong.OngController.ROUTE_ONG;


@RestController
@RequestMapping(ROUTE_ONG)
public class OngController {

    @Autowired
    private OngService service;
    public static final String ROUTE_ONG = "/v1/ong";

    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public String homePage() {
        return "HOME PAGE PUBLICA DA ONG";
    }

}

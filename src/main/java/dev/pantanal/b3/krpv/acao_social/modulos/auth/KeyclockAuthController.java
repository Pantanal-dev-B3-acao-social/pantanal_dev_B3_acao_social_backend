package dev.pantanal.b3.krpv.acao_social.modulos.auth;

import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static dev.pantanal.b3.krpv.acao_social.modulos.auth.KeyclockAuthController.ROUTE_AUTH;

@RequestMapping(ROUTE_AUTH)
@RestController
public class KeyclockAuthController {
    @Autowired
    private KeyclockAuthService service;
    public static final String ROUTE_AUTH = "/v1/auth";
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDto userDto) {
        // TODO: parece que esta executando 2x, causando lentidão
        var result = service.loginUser(userDto);
        return result;
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logoutUser(@RequestBody LoginUserDto userDto) {
//        var result = service.logoutUser(userDto);
//        return result;
//    }

}

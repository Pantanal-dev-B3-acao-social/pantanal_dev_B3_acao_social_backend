package dev.pantanal.b3.krpv.acao_social.modulos.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static dev.pantanal.b3.krpv.acao_social.modulos.user.UserController.ROUTE_USER;

@RequestMapping(ROUTE_USER)
@RestController
public class UserController {
    @Autowired
    private UserService service;


    public static final String ROUTE_USER = "/v1/user";

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> userProfile(JwtAuthenticationToken userLogged, @PathVariable UUID userId) {
        var result = service.userProfile(userId, userLogged );
        return result;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<String> updateUser(@PathVariable UUID userId) {
//        var result = service.updateUser(userId);
//        return result;
//    }
}

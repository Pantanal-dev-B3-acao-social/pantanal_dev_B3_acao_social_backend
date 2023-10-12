package dev.pantanal.b3.krpv.acao_social.modulos.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.user.UserController.ROUTE_USER;

@RequestMapping(ROUTE_USER)
@RestController
public class UserController {
    @Autowired
    private UserService service;

    public static final String ROUTE_USER = "/v1/user";

    @GetMapping("/{userId}/profile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> profile(JwtAuthenticationToken userLogged, @PathVariable UUID userId) {
        ResponseEntity<String> result = this.service.userProfile(userId, userLogged);
        return result;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> findAll(
//        @QueryParam("username") String username,
//        @QueryParam("firstName") String firstName,
//        @QueryParam("lastName") String lastName,
//        @QueryParam("email") String email,
//        @QueryParam("first") Integer first,
//        @QueryParam("max") Integer max
    ) {
        ResponseEntity<String> result = this.service.findAll(/* username, firstName, lastName, email, first, max*/);
        return result;
    }


}


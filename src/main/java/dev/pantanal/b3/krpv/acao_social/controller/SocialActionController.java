package dev.pantanal.b3.krpv.acao_social.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class SocialActionController {

    @GetMapping
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok("todos os usuarios");
    }

}

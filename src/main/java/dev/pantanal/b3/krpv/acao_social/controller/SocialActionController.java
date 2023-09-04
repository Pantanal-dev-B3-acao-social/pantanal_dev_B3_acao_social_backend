package dev.pantanal.b3.krpv.acao_social.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class SocialActionController {

    @GetMapping
    public String getAllUsers() {
        return "List of users";
    }

}

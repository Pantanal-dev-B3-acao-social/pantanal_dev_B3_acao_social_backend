package dev.pantanal.b3.krpv.acao_social.modulos.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.pantanal.b3.krpv.acao_social.config.postgres.factory.CategoryGroupFactory;
import dev.pantanal.b3.krpv.acao_social.modulos.auth.dto.LoginUserDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.CredentialDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserCreateDto;
import dev.pantanal.b3.krpv.acao_social.utils.GenerateTokenUserForLogged;
import dev.pantanal.b3.krpv.acao_social.utils.LoginMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.*;

//@Component
public class UserFactory {

    private static final Random random = new Random();
    private final Faker faker = new Faker();
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;
    private String tokenUserLogged;
//    public UserFactory (String tokenUserLogged) {
//        this.tokenUserLogged = tokenUserLogged;
//    }
    public UserFactory(String tokenUserLogged, ObjectMapper objectMapper, UserService userService) {
        this.tokenUserLogged = tokenUserLogged;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    public UserCreateDto makeUserDto() {
        List<CredentialDto> credentials = new ArrayList<>();
        Boolean enabled = true;
        credentials.add(
                new CredentialDto(
                        "password",
                        "123456",
                        false
                )
        );
        UserCreateDto dto = new UserCreateDto(
                faker.name().username(),
                faker.random().nextBoolean(),
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.name().lastName(),
                Collections.emptyList()
        );
        return dto;
    }
    public KeycloakUser createOne() {
        UserCreateDto dto = makeUserDto();
        UUID userId = userService.create(dto, this.tokenUserLogged);
        KeycloakUser keycloakUser = userService.findById(userId, tokenUserLogged);
        return keycloakUser;
    }

    public List<KeycloakUser> createMany(Integer amount) {
        List<KeycloakUser> keycloakUsers = new ArrayList<>();
        for (int i=0; i < amount; i++) {
            KeycloakUser user = this.createOne();
            keycloakUsers.add(user);
        }
        return keycloakUsers;
    }

}

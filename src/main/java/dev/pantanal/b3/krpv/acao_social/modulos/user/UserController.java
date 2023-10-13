package dev.pantanal.b3.krpv.acao_social.modulos.user;

import dev.pantanal.b3.krpv.acao_social.modulos.category.modules.categoryGroup.dto.response.CategoryGroupResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.user.UserController.ROUTE_USER;

@RequestMapping(ROUTE_USER)
@RestController
//@PreAuthorize("hasAnyRole('USER')")
public class UserController {
    @Autowired
    private UserService service;

    public static final String ROUTE_USER = "/v1/user";

    @GetMapping("/{userId}/profile")
    @PreAuthorize("hasAnyRole('USER_GET_ONE')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<KeycloakUser> findById(JwtAuthenticationToken userLogged, @PathVariable UUID userId) {
        KeycloakUser keycloakUser = this.service.findById(userId, userLogged.getToken().toString());
        return new ResponseEntity<>(keycloakUser, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER_GET_ALL')")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER_CREATE')")
    @Operation(summary = "Creates an User", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User succesfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating User"),
    })
    public ResponseEntity<UUID> create(@RequestBody @Valid UserCreateDto request) {
        UUID userId = service.create(request);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updates an Social Action", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Social Action no found"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Error when creating User"),
    })
    public ResponseEntity<String> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDto dto) {
        ResponseEntity<String> response = service.update(id, dto);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes an user", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Id"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Error when excluding User"),
    })
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}


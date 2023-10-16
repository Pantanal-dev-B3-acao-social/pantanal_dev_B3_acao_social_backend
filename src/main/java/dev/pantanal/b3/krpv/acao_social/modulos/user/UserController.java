package dev.pantanal.b3.krpv.acao_social.modulos.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserCreateDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserResponseDto;
import dev.pantanal.b3.krpv.acao_social.modulos.user.dto.UserUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static dev.pantanal.b3.krpv.acao_social.modulos.user.UserController.ROUTE_USER;

@RequestMapping(ROUTE_USER)
@RestController
//@PreAuthorize("hasAnyRole('USER')")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    ObjectMapper objectMapper;

    public static final String ROUTE_USER = "/v1/user";

    public List<UserResponseDto> mapEntityToDto(ResponseEntity<String> response) {
        List<UserResponseDto> dtos = new ArrayList<>();
        String responseBody = response.getBody();
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            if (jsonNode.isArray()) {
                for (JsonNode element : jsonNode) {
                    UserResponseDto dto = new UserResponseDto(
                        element.get("id").asText(),
                        element.get("username").asText(),
                        element.get("enabled").asBoolean(),
                        element.get("totp").asBoolean(),
                        element.get("email") == null ? null : element.get("email").asText(),
                        element.get("firstName").asText(),
                        element.get("lastName").asText(),
                        objectMapper.convertValue(element.get("credentials"), Map.class),
                        element.get("emailVerified").asBoolean(),
                        objectMapper.convertValue(element.get("attributes"), Map.class),
                        element.get("self") == null ? null : element.get("self").asText(),
                        element.get("origin") == null ? null : element.get("origin").asText(),
                        element.get("createdTimestamp").asLong(),
                        element.get("federationLink") == null ? null : element.get("federationLink").asText(),
                        element.get("serviceAccountClientId")== null ? null : element.get("serviceAccountClientId").asText(),
                        objectMapper.convertValue(element.get("disableableCredentialTypes"), List.class)
                    );
                    dtos.add(dto);
                }
            }
            return dtos;
        } catch (Exception e) {
            throw new RuntimeException(" Erro ao criar UserResponseDto.java ", e);
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('USER_GET_ONE')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<KeycloakUser> findById(JwtAuthenticationToken userLogged, @PathVariable UUID userId) {
        KeycloakUser keycloakUser = this.service.findById(userId, userLogged.getToken().getTokenValue());
        return new ResponseEntity<>(keycloakUser, HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER_GET_ALL')")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponseDto> findAll (
            JwtAuthenticationToken userLogged,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @SortDefault(sort="name", direction = Sort.Direction.DESC) Sort sort
//        @QueryParam("username") String username,
//        @QueryParam("firstName") String firstName,
//        @QueryParam("lastName") String lastName,
//        @QueryParam("email") String email,
//        @QueryParam("first") Integer first,
//        @QueryParam("max") Integer max
    ) {
        Pageable paging = PageRequest.of(page, size, sort);
        ResponseEntity<String> result = this.service.findAll(
                page,
                size,
                userLogged.getToken().getTokenValue()
                /* username, firstName, lastName, email, first, max*/);
        List<UserResponseDto> dtos = mapEntityToDto(result);
        return new PageImpl<>(dtos, paging, dtos.size());
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
    public ResponseEntity<String> create(
            JwtAuthenticationToken userLogged,
            @RequestBody @Valid UserCreateDto request
    ) {
        UUID userId = service.create(request, userLogged.getToken().getTokenValue());
        return new ResponseEntity<>(userId.toString(), HttpStatus.OK);
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
    public ResponseEntity<String> update(
        JwtAuthenticationToken userLogged,
        @PathVariable UUID id,
        @Valid @RequestBody UserUpdateDto dto
    ) {
        ResponseEntity<String> response = service.update(id, dto, userLogged.getToken().getTokenValue());
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
    public void delete(
        JwtAuthenticationToken userLogged,
        @PathVariable UUID id
    ) {
        service.delete(id, userLogged.getToken().getTokenValue());
    }

}


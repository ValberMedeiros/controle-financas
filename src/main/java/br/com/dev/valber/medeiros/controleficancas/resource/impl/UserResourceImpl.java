package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import br.com.dev.valber.medeiros.controleficancas.exception.error.StandardError;
import br.com.dev.valber.medeiros.controleficancas.resource.UserResource;
import br.com.dev.valber.medeiros.controleficancas.service.impl.UserServiceImpl;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@RestController
@RequestMapping("/api/users")
@Api(value = "User Service", tags = {"User creation"})
public class UserResourceImpl implements UserResource {

    private final UserServiceImpl userService;

    public UserResourceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    @ApiOperation(
            value = "Busca todos os usuários",
            tags = {"User creation"},
            authorizations = {@Authorization("JWT")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<UserDTO>>> findAll() {
        return ResponseEntity.ok(ObjectDataResponse.build(userService.findAll()));
    }

    @Override
    @ApiOperation(
            value = "Cria um usuário",
            tags = {"User creation"},
            authorizations = {@Authorization("JWT")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<UserDTO>> create(
            @Valid @RequestBody UserRequestDTO user
    ) throws URISyntaxException {
        return ResponseEntity.created(new URI("")).body(ObjectDataResponse.build(userService.create(user)));
    }

    @Override
    @ApiOperation(
            value = "Atualiza um usuários por uuid",
            tags = {"User creation"},
            authorizations = {@Authorization("JWT")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @PutMapping(value = "/{uuid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<UserDTO>> update(
            @ApiParam(required = true, value = "UUID do usuário a ser atualizado.")
            @PathVariable UUID uuid,
            @Valid @RequestBody() UserRequestDTO user) {
        return ResponseEntity.ok(ObjectDataResponse.build(userService.update(user, uuid)));
    }

    @Override
    @ApiOperation(
            value = "Deleta um usuário por uuid",
            tags = {"User creation"},
            authorizations = {@Authorization("JWT")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity<Void> delete(
            @ApiParam(required = true, value = "UUID do usuário a ser deletado.")
            @PathVariable UUID uuid) {
        userService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

    @Override
    @ApiOperation(
            value = "Busca um usuários por uuid",
            tags = {"User creation"},
            authorizations = {@Authorization("JWT")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(value = "/{username}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<UserDTO>> findByUsername(
            @ApiParam(required = true, value = "Username a ser buscado.")
            @PathVariable String username) {
        return ResponseEntity.ok(ObjectDataResponse.build(userService.findByUsername(username)));
    }

    @Override
    @ApiOperation(
            value = "Recupera novo access_token",
            tags = {"User creation"},
            authorizations = {@Authorization("JWT")}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.getAccessToken(request, response);
    }
}

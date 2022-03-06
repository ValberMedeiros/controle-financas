package br.com.dev.valber.medeiros.controleficancas.resource;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface UserResource {

    @GetMapping("/users")
    public ResponseEntity<ObjectDataResponse<List<UserDTO>>> findAll();

    @PostMapping("/users")
    public ResponseEntity<ObjectDataResponse<UserDTO>> create(UserRequestDTO user) throws URISyntaxException;

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<UserDTO>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestDTO user
    );

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    );

    @GetMapping(value = "{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<UserDTO>> findByUsername(
            @PathVariable String username
    );

}

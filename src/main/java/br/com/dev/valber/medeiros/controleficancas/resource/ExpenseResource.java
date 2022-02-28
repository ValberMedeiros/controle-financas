package br.com.dev.valber.medeiros.controleficancas.resource;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface ExpenseResource {

    @GetMapping("/expenses")
    public ResponseEntity<ObjectDataResponse<List<ExpenseDTO>>> findAll();

    @PostMapping("/expenses")
    public ResponseEntity<ObjectDataResponse<ExpenseDTO>> create(ExpenseRequestDTO dto);

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<ExpenseDTO>> update(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseRequestDTO expenseRequestDTO
    );

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    );

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<ExpenseDTO>> findById(
            @PathVariable UUID id
    );

}

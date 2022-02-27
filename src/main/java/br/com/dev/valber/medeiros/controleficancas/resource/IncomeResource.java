package br.com.dev.valber.medeiros.controleficancas.resource;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface IncomeResource {

    @GetMapping("/incomes")
    public ResponseEntity<ObjectDataResponse<List<IncomeDTO>>> findAll();

    @PostMapping("/incomes")
    public ResponseEntity<ObjectDataResponse<IncomeDTO>> create(IncomeRequestDTO dto);

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<IncomeDTO>> update(
            @PathVariable UUID id,
            @Valid @RequestBody IncomeRequestDTO incomeRequestDTO
    );

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    );

}

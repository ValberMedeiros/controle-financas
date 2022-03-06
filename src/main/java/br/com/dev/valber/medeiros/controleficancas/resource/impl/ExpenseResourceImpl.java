package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import br.com.dev.valber.medeiros.controleficancas.exception.error.StandardError;
import br.com.dev.valber.medeiros.controleficancas.resource.ExpenseResource;
import br.com.dev.valber.medeiros.controleficancas.service.impl.ExpenseServiceImpl;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
@Api(value = "Expense Service", tags = {"Expense creation"})
public class ExpenseResourceImpl implements ExpenseResource {

    private ExpenseServiceImpl expenseService;

    public ExpenseResourceImpl(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
    }

    @Override
    @ApiOperation(value = "Retorna a lista de rendas", tags = {"Expense creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<ExpenseDTO>>> findAll() {
        return ResponseEntity.ok(ObjectDataResponse.build(expenseService.findAll()));
    }

    @Override
    @ApiOperation(value = "Cadastra uma nova renda", tags = {"Expense creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ObjectDataResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<ExpenseDTO>> create(@Valid @RequestBody ExpenseRequestDTO expenseRequestDTO) {
        return ResponseEntity.created(URI.create("")).body(ObjectDataResponse.build(expenseService.create(expenseRequestDTO)));
    }

    @Override
    @ApiOperation(value = "Atualiza uma renda existente", tags = {"Expense creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ObjectDataResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<ExpenseDTO>> update(
            @ApiParam(required = true, value = "UUID da renda a ser atualizada.")
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseRequestDTO expenseRequestDTO
    ) {
        return ResponseEntity.ok().body(ObjectDataResponse.build(expenseService.update(id, expenseRequestDTO)));
    }

    @Override
    @ApiOperation(value = "Deleta uma renda existente", tags = {"Expense creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(
            @ApiParam(required = true, value = "UUID da renda a ser atualizada.")
            @PathVariable UUID id
    ) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @ApiOperation(value = "Busca uma renda existente por id", tags = {"Expense creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    public ResponseEntity<ObjectDataResponse<ExpenseDTO>> findById(
            @ApiParam(required = true, value = "UUID da renda a ser buscada.")
            @PathVariable() UUID id
    ) {
        return ResponseEntity.ok(ObjectDataResponse.build(expenseService.findById(id)));
    }
}

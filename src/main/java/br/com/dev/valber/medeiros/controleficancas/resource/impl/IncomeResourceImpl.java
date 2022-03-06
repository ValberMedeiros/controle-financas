package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import br.com.dev.valber.medeiros.controleficancas.exception.error.StandardError;
import br.com.dev.valber.medeiros.controleficancas.resource.IncomeResource;
import br.com.dev.valber.medeiros.controleficancas.service.impl.IncomeServiceImpl;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incomes")
@Api(value = "Income Service", tags = {"Income creation"})
public class IncomeResourceImpl implements IncomeResource {

    private IncomeServiceImpl incomeService;

    public IncomeResourceImpl(IncomeServiceImpl incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    @ApiOperation(value = "Retorna a lista de rendas", tags = {"Income creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<IncomeDTO>>> findAll() {
        return ResponseEntity.ok(ObjectDataResponse.build(incomeService.findAll()));
    }

    @Override
    @ApiOperation(value = "Cadastra uma nova renda", tags = {"Income creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = ObjectDataResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<IncomeDTO>> create(@Valid @RequestBody IncomeRequestDTO incomeRequestDTO) {
        return ResponseEntity.created(URI.create("")).body(ObjectDataResponse.build(incomeService.create(incomeRequestDTO)));
    }

    @Override
    @ApiOperation(value = "Atualiza uma renda existente", tags = {"Income creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ObjectDataResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<IncomeDTO>> update(
            @ApiParam(required = true, value = "UUID da renda a ser atualizada.")
            @PathVariable UUID id,
            @Valid @RequestBody IncomeRequestDTO incomeRequestDTO
    ) {
        return ResponseEntity.ok().body(ObjectDataResponse.build(incomeService.update(incomeRequestDTO, id)));
    }

    @Override
    @ApiOperation(value = "Deleta uma renda existente", tags = {"Income creation"})
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
        incomeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

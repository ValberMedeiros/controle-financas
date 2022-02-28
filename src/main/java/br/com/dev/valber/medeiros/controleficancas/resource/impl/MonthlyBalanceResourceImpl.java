package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDateReferenceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.TotalBalanceExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import br.com.dev.valber.medeiros.controleficancas.exception.error.DefaultError;
import br.com.dev.valber.medeiros.controleficancas.resource.MonthlyBalanceResource;
import br.com.dev.valber.medeiros.controleficancas.service.impl.MonthlyBalanceServiceImpl;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/balances")
@Api(value = "Balances Service", tags = {"Balance creation"})
public class MonthlyBalanceResourceImpl implements MonthlyBalanceResource {

    private MonthlyBalanceServiceImpl service;

    public MonthlyBalanceResourceImpl(MonthlyBalanceServiceImpl service) {
        this.service = service;
    }

    @Override
    @ApiOperation(value = "Retorna a lista de ano/mes referência de balanço", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = DefaultError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = DefaultError.class),
            @ApiResponse(code = 404, message = "Not Found", response = DefaultError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = DefaultError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = DefaultError.class),
    })
    @GetMapping(value = "/references", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<MonthlyBalanceDateReferenceDTO>>> getMonthlyBalanceDateReferences() {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getMonthlyBalanceReferencesDate()));
    }

    @Override
    @ApiOperation(value = "Retorna a lista de despesas referente a um ano/mês de balanço", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = DefaultError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = DefaultError.class),
            @ApiResponse(code = 404, message = "Not Found", response = DefaultError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = DefaultError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = DefaultError.class),
    })
    @GetMapping(value = "/references/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<ExpenseDTO>>> getExpensesForMonthlyBalances(
            @ApiParam(value = "Ano/Mês referência do balanço", example = "2022-05")
            @RequestParam() String reference
            ) {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getExpensesForMonthlyBalances(reference)));
    }

    @Override
    @ApiOperation(value = "Retorna a lista de despesas referente aum ano/mês de balanço", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = DefaultError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = DefaultError.class),
            @ApiResponse(code = 404, message = "Not Found", response = DefaultError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = DefaultError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = DefaultError.class),
    })
    @GetMapping(value = "/references/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<TotalBalanceExpenseDTO>> getTotalBalanceExpense(String reference) {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getTotalBalanceExpense(reference)));
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<MonthlyBalanceDTO>> create(
            @RequestBody() MonthlyBalanceRequestDTO monthlyBalanceRequestDTO
    ) {
        return ResponseEntity.created(URI.create("")).body(ObjectDataResponse.build(service.create(monthlyBalanceRequestDTO)));
    }
}

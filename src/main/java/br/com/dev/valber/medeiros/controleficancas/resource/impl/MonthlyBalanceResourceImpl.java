package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.*;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import br.com.dev.valber.medeiros.controleficancas.exception.error.StandardError;
import br.com.dev.valber.medeiros.controleficancas.resource.MonthlyBalanceResource;
import br.com.dev.valber.medeiros.controleficancas.service.impl.MonthlyBalanceServiceImpl;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(value = "/references", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<MonthlyBalanceDateReferenceDTO>>> getMonthlyBalanceDateReferences() {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getMonthlyBalanceReferencesDate()));
    }

    @Override
    @ApiOperation(value = "Retorna a lista de despesas referente a um ano/mês de balanço", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(value = "/references/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<ExpenseDTO>>> getExpensesForMonthlyBalances(
            @ApiParam(value = "Ano/Mês referência do balanço", example = "2022-05")
            @RequestParam() String reference
            ) {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getExpensesForMonthlyBalances(reference)));
    }

    @Override
    @ApiOperation(value = "Retorna o balanço de despesas referente a um ano/mês", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(value = "/references/balance/expense", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<TotalBalanceDTO>> getTotalBalanceExpense(
            @ApiParam(value = "Ano/Mês referência do balanço", example = "2022-04")
            @RequestParam() String reference) {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getTotalBalanceExpense(reference)));
    }

    @Override
    @ApiOperation(value = "Cria um novo balanço referente a um ano/mês", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<MonthlyBalanceDTO>> create(
            @Valid @RequestBody() MonthlyBalanceRequestDTO monthlyBalanceRequestDTO
    ) {
        return ResponseEntity.created(URI.create("")).body(ObjectDataResponse.build(service.create(monthlyBalanceRequestDTO)));
    }

    @Override
    @ApiOperation(value = "Retorna a lista de rendas referente aum ano/mês de balanço", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping("/references/incomes")
    public ResponseEntity<ObjectDataResponse<List<IncomeDTO>>> getIncomesForMonthlyBalances(
            @ApiParam(value = "Ano/Mês referência do balanço", example = "2022-04")
            @RequestParam() String reference) {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getIncomesForMonthlyBalance(reference)));
    }

    @Override
    @ApiOperation(value = "Retorna o balanço de rendas referente a um ano/mês", tags = {"Balance creation"})
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized", response = StandardError.class),
            @ApiResponse(code = 403, message = "Forbidden", response = StandardError.class),
            @ApiResponse(code = 404, message = "Not Found", response = StandardError.class),
            @ApiResponse(code = 422, message = "Unprocessable Entity", response = StandardError.class),
            @ApiResponse(code = 422, message = "Internal Server Error", response = StandardError.class),
    })
    @GetMapping(value = "/references/balance/incomes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<TotalBalanceDTO>> getTotalBalanceIncomes(
            @ApiParam(value = "Ano/Mês referência do balanço", example = "2022-04")
            @RequestParam() String reference) {
        return ResponseEntity.ok(ObjectDataResponse.build(service.getTotalBalanceIncome(reference)));
    }
}

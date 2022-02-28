package br.com.dev.valber.medeiros.controleficancas.resource;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDateReferenceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.TotalBalanceExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.response.ObjectDataResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MonthlyBalanceResource {

    @GetMapping("/balances")
    public ResponseEntity<ObjectDataResponse<List<MonthlyBalanceDateReferenceDTO>>> getMonthlyBalanceDateReferences();

    @GetMapping(value = "/references/incomes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<List<ExpenseDTO>>> getExpensesForMonthlyBalances(
            @RequestParam String reference
    );

    @GetMapping(value = "/references/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<TotalBalanceExpenseDTO>> getTotalBalanceExpense(
            @RequestParam String reference
    );

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectDataResponse<MonthlyBalanceDTO>> create(
            @RequestBody MonthlyBalanceRequestDTO monthlyBalanceRequestDTO
            );
}

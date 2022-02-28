package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDateReferenceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.TotalBalanceExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;

import java.util.List;

public interface MonthlyBalanceService {

    public List<MonthlyBalanceDateReferenceDTO> getMonthlyBalanceReferencesDate();

    public List<ExpenseDTO> getExpensesForMonthlyBalances(String reference);

    public TotalBalanceExpenseDTO getTotalBalanceExpense(String referenceDate);

    public MonthlyBalanceDTO create(MonthlyBalanceRequestDTO monthlyBalanceRequestDTO);

}

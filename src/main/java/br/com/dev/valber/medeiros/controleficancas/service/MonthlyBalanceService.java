package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.*;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;

import java.util.List;

public interface MonthlyBalanceService {

    public List<MonthlyBalanceDateReferenceDTO> getMonthlyBalanceReferencesDate();

    public List<ExpenseDTO> getExpensesForMonthlyBalances(String referenceDate);

    public TotalBalanceDTO getTotalBalanceExpense(String referenceDate);

    public MonthlyBalanceDTO create(MonthlyBalanceRequestDTO monthlyBalanceRequestDTO);

    public List<IncomeDTO> getIncomesForMonthlyBalance(String referenceDate);

    public TotalBalanceDTO getTotalBalanceIncome(String referenceDate);

}

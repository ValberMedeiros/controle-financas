package br.com.dev.valber.medeiros.controleficancas.repository;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDateReferenceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.TotalBalanceExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MonthlyBalanceRepository {

    TotalBalanceExpenseDTO getTotalBalanceExpense(LocalDate reference);

    List<ExpenseDTO> getExpensesForMonthlyBalances(LocalDate reference);

    List<MonthlyBalanceDateReferenceDTO> getMonthlyBalanceDateReferences();

    int create(MonthlyBalanceRequestDTO entity);

    MonthlyBalanceDTO update(MonthlyBalanceRequestDTO entity, UUID uuid);

    void delete(UUID uuid);

    MonthlyBalanceDTO getMonthlyBalance(UUID uuid);

    UUID getUuidMonthlyBalanceByReferenceDate(LocalDate reference);

}

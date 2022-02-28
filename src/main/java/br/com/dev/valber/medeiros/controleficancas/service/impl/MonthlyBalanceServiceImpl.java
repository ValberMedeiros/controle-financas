package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDateReferenceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.TotalBalanceExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.MonthlyBalanceRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.MonthlyBalanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.dev.valber.medeiros.controleficancas.utils.DateUtils.complementDate;
import static br.com.dev.valber.medeiros.controleficancas.utils.DateUtils.stringToDate;

@Service
public class MonthlyBalanceServiceImpl implements MonthlyBalanceService {

    private final MonthlyBalanceRepositoryImpl repository;

    public MonthlyBalanceServiceImpl(MonthlyBalanceRepositoryImpl totalRepository) {
        this.repository = totalRepository;
    }

    @Override
    public List<MonthlyBalanceDateReferenceDTO> getMonthlyBalanceReferencesDate() {
        return repository.getMonthlyBalanceDateReferences();
    }

    @Override
    public List<ExpenseDTO> getExpensesForMonthlyBalances(String reference) {
        return repository.getExpensesForMonthlyBalances(stringToDate(reference));
    }

    @Override
    public TotalBalanceExpenseDTO getTotalBalanceExpense(String referenceDate) {
        return repository.getTotalBalanceExpense(stringToDate(referenceDate));
    }

    @Override
    public MonthlyBalanceDTO create(MonthlyBalanceRequestDTO monthlyBalanceRequestDTO) {
        monthlyBalanceRequestDTO.setUuid(UUID.randomUUID());
        monthlyBalanceRequestDTO.setReferenceDate(complementDate(monthlyBalanceRequestDTO.getReferenceDate()));

        return repository.getMonthlyBalance(monthlyBalanceRequestDTO.getUuid());
    }
}

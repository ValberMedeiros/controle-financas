package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.*;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.MonthlyBalanceRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.MonthlyBalanceService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        try{
            return repository.getExpensesForMonthlyBalances(stringToDate(reference));
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public TotalBalanceDTO getTotalBalanceExpense(String referenceDate) {
        try{
            return repository.getTotalBalanceExpense(stringToDate(referenceDate));
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException(String.format("Total balance expenses for reference date %s not found.", referenceDate), "entity.not.found.exception");
        }
    }

    @Override
    public MonthlyBalanceDTO create(MonthlyBalanceRequestDTO monthlyBalanceRequestDTO) {
        monthlyBalanceRequestDTO.setUuid(UUID.randomUUID());
        monthlyBalanceRequestDTO.setReferenceDate(complementDate(monthlyBalanceRequestDTO.getReferenceDate()));
        repository.create(monthlyBalanceRequestDTO);
        return repository.getMonthlyBalance(monthlyBalanceRequestDTO.getUuid());
    }

    @Override
    public List<IncomeDTO> getIncomesForMonthlyBalance(String referenceDate) {
        try {
            return repository.getIncomesForMonthlyBalance(stringToDate(referenceDate));
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public TotalBalanceDTO getTotalBalanceIncome(String referenceDate) {
        try {
            return repository.getTotalBalanceIncomes(stringToDate(referenceDate));
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException(String.format("Total balance incomes for reference date %s not found.", referenceDate), "entity.not.found.exception");
        }
    }
}

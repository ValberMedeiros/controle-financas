package br.com.dev.valber.medeiros.controleficancas.repository;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository {

    int create(ExpenseRequestDTO entity);

    List<ExpenseDTO> findAll();

    ExpenseDTO findById(UUID uuid);

    void delete(UUID uuid);

    int update(ExpenseRequestDTO entity, UUID uuid);

    int updateStatus(ExpenseStatus status, UUID uuid);

    List<ExpenseDTO> getExpenseWithDueDebt();

}

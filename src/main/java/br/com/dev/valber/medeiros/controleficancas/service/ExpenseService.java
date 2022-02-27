package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    public List<ExpenseDTO> findAll();

    public ExpenseDTO findById(UUID uuid);

    public ExpenseDTO update(UUID uuid, ExpenseRequestDTO requestDTO);

    public ExpenseDTO create(ExpenseRequestDTO requestDTO);

    public void delete(UUID uuid);

}

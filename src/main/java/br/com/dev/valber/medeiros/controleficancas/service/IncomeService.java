package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.Income;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomeService {

    public List<IncomeDTO> findAll();

    public Optional<Income> findById(UUID id);

    public IncomeDTO create(IncomeRequestDTO income);

    public IncomeDTO update(UUID id, IncomeRequestDTO dto);

    public void delete(UUID id);

}

package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;

import java.util.List;
import java.util.UUID;

public interface IncomeService {

    public List<IncomeDTO> findAll();

    public IncomeDTO findById(UUID id);

    public IncomeDTO create(IncomeRequestDTO income);

    public IncomeDTO update(IncomeRequestDTO dto, UUID id);

    public void delete(UUID id);

}

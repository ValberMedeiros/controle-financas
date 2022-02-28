package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.IncomeRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.IncomeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepositoryImpl repository;

    public IncomeServiceImpl(IncomeRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public IncomeDTO findById(UUID id) {
        return null;
    }

    @Override
    public List<IncomeDTO> findAll() {
        return repository.findAll();
    }

    @Override
    public IncomeDTO create(IncomeRequestDTO income) {
        income.setUuid(UUID.randomUUID());
        repository.create(income);
        return repository.findById(income.getUuid());
    }

    @Override
    public IncomeDTO update(IncomeRequestDTO dto, UUID id) {
        repository.update(dto, id);
        return repository.findById(id);
    }

    @Override
    public void delete(UUID uuid) {
        repository.delete(uuid);
    }

}

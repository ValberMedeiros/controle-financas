package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.IncomeRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.IncomeService;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public IncomeDTO findById(UUID uuid) {
        try {
            return repository.findById(uuid);
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException(String.format("Income with uuid %s not found.", uuid), "entity.not.found.exception");
        }
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
    public IncomeDTO update(IncomeRequestDTO dto, UUID uuid) {
        try {
            repository.update(dto, uuid);
            return repository.findById(uuid);
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException(String.format("Income with uuid %s not found.", uuid), "entity.not.found.exception");
        }
    }

    @Override
    public void delete(UUID uuid) {
        findById(uuid);
        repository.delete(uuid);
    }

}

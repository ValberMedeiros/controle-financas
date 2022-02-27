package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.mapper.ExpenseMapper;
import br.com.dev.valber.medeiros.controleficancas.repository.income.ExpenseRepository;
import br.com.dev.valber.medeiros.controleficancas.service.ExpenseService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository repository;
    private ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseRepository repository, ExpenseMapper expenseMapper) {
        this.repository = repository;
        this.expenseMapper = expenseMapper;
    }

    @Override
    public List<ExpenseDTO> findAll() {
        return expenseMapper.listToDto(repository.findAll());
    }

    @Override
    public ExpenseDTO findById(UUID uuid) {
        var expense = Optional.ofNullable(repository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("entity.not.found")));
        return expenseMapper.entitieToDto(expense.get());
    }

    @Override
    public ExpenseDTO update(UUID uuid, ExpenseRequestDTO requestDTO) {
        repository.update(requestDTO, uuid);
        return expenseMapper.entitieToDto(repository.findById(uuid)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public ExpenseDTO create(ExpenseRequestDTO requestDTO) {
        return expenseMapper.entitieToDto(repository.save(expenseMapper.dtoToEntity(requestDTO)));
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }
}

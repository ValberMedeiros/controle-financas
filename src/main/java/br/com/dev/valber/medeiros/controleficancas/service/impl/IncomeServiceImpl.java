package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.mapper.IncomeMapper;
import br.com.dev.valber.medeiros.controleficancas.repository.income.IncomeRepository;
import br.com.dev.valber.medeiros.controleficancas.service.IncomeService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository repository;
    private final IncomeMapper incomeMapper;

    public IncomeServiceImpl(IncomeRepository repository, IncomeMapper expenseMapper) {
        this.repository = repository;
        this.incomeMapper = expenseMapper;
    }

    @Override
    public IncomeDTO findById(UUID id) {
        var income = Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity.not.found")));
        return incomeMapper.entitieToDto(income.get());
    }

    @Override
    public List<IncomeDTO> findAll() {
        var entities = repository.findAll();
        return incomeMapper.listToDto(entities);
    }

    @Override
    public IncomeDTO create(IncomeRequestDTO income) {
        return incomeMapper.entitieToDto(repository.save(incomeMapper.dtoToEntity(income)));
    }

    @Override
    public IncomeDTO update(UUID id, IncomeRequestDTO dto) {
        repository.update(dto, id);
        return incomeMapper.entitieToDto(repository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}

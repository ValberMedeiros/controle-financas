package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.Income;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.income.IncomeRepository;
import br.com.dev.valber.medeiros.controleficancas.service.IncomeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository repository;
    private final ModelMapper modelMapper;

    public IncomeServiceImpl(IncomeRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<Income> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("entity.not.found")));
    }

    @Override
    public List<IncomeDTO> findAll() {
        var entities = repository.findAll();
        return listToDto(entities);
    }

    @Override
    public IncomeDTO create(IncomeRequestDTO income) {
        return entitieToDto(repository.save(dtoToEntity(income)));
    }

    @Override
    public IncomeDTO update(UUID id, IncomeRequestDTO dto) {
        dto.setUuid(id);
        return this.create(dto);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private List<IncomeDTO> listToDto(List<Income>entities) {
        return entities.stream()
                .map(this::entitieToDto)
                .toList();

    }

    private IncomeDTO entitieToDto(Income entitie) {
        return modelMapper.map(entitie, IncomeDTO.class);
    }

    private Income dtoToEntity(IncomeRequestDTO dto) {
        return modelMapper.map(dto, Income.class);
    }
}

package br.com.dev.valber.medeiros.controleficancas.mapper;

import br.com.dev.valber.medeiros.controleficancas.domain.Income;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncomeMapper {

    private ModelMapper modelMapper;

    public IncomeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<IncomeDTO> listToDto(List<Income> entities) {
        return entities.stream()
                .map(this::entitieToDto)
                .toList();
    }

    public IncomeDTO entitieToDto(Income entitie) {
        return modelMapper.map(entitie, IncomeDTO.class);
    }

    public Income dtoToEntity(IncomeRequestDTO dto) {
        return modelMapper.map(dto, Income.class);
    }

}

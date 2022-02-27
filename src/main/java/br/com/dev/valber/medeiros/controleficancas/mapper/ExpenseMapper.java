package br.com.dev.valber.medeiros.controleficancas.mapper;

import br.com.dev.valber.medeiros.controleficancas.domain.Expense;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseMapper {

    private ModelMapper modelMapper;

    public ExpenseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<ExpenseDTO> listToDto(List<Expense> entities) {
        return entities.stream()
                .map(this::entitieToDto)
                .toList();
    }

    public ExpenseDTO entitieToDto(Expense entitie) {
        return modelMapper.map(entitie, ExpenseDTO.class);
    }

    public Expense dtoToEntity(ExpenseRequestDTO dto) {
        return modelMapper.map(dto, Expense.class);
    }

}

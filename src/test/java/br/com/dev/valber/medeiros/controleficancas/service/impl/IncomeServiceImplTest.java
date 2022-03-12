package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.IncomeRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class IncomeServiceImplTest {

    @InjectMocks
    private IncomeServiceImpl incomeService;

    @Mock
    private IncomeRepositoryImpl incomeRepository;

    @Test
    void findById() {
        IncomeDTO incomeDTO = getIncome();
        when(incomeRepository.findById(any())).thenReturn(getIncome());

        IncomeDTO response = incomeService.findById(incomeDTO.getUuid());
        assertEquals("teste", response.getDescription());
    }

    @Test
    void findByIdSholdTrhowBusinessExceptionWhenIncomeNotFound() {
        UUID uuid = UUID.randomUUID();
        when(incomeRepository.findById(any())).thenThrow(EmptyResultDataAccessException.class);
        Exception exception = assertThrows(BusinessException.class, () -> {
            incomeService.findById(uuid);
        });
        assertEquals(String.format("Income with uuid %s not found.", uuid), exception.getMessage());
    }

    @Test
    void findAll() {
        List<IncomeDTO> incomes = getIncomes();
        when(incomeRepository.findAll()).thenReturn(incomes);

        List<IncomeDTO> response = incomeService.findAll();
        assertNotNull(response);
        assertEquals("teste", incomes.get(0).getDescription());
    }

    @Test
    void create() {
        when(incomeRepository.create(any(IncomeRequestDTO.class))).thenReturn(1);
        when(incomeRepository.findById(any())).thenReturn(getIncome());

        IncomeDTO incomeDTO = incomeService.create(getRequestDTO());
        assertEquals("teste", incomeDTO.getDescription());
    }

    @Test
    void update() {

        when(incomeRepository.update(any(IncomeRequestDTO.class), any())).thenReturn(1);
        when(incomeRepository.findById(any())).thenReturn(getIncome());

        IncomeDTO update = incomeService.update(getRequestDTO(), UUID.randomUUID());
        assertEquals("teste", update.getDescription());
    }

    @Test
    void updateSholdThrowBusinessExceptionWhenIncomeNotFound() {
        UUID uuid = UUID.randomUUID();
        IncomeRequestDTO requestDTO = getRequestDTO();
        when(incomeRepository.update(any(IncomeRequestDTO.class), any())).thenReturn(1);
        when(incomeRepository.findById(any())).thenThrow(EmptyResultDataAccessException.class);
        Exception exception = assertThrows(BusinessException.class, () -> {
            incomeService.update(requestDTO, uuid);
        });
        assertEquals(String.format("Income with uuid %s not found.", uuid), exception.getMessage());
    }

    @Test
    void delete() {
        incomeService.delete(UUID.randomUUID());
        verify(incomeRepository, times(1)).delete(any());
    }

    private IncomeDTO getIncome() {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setAmount(BigDecimal.TEN);
        incomeDTO.setDescription("teste");
        incomeDTO.setUuid(UUID.randomUUID());
        incomeDTO.setMonthlyBalanceReferenceDate(LocalDate.now());
        incomeDTO.setReceiptDate(LocalDate.now());

        return incomeDTO;
    }

    private List<IncomeDTO> getIncomes() {
        List<IncomeDTO> incomes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            incomes.add(getIncome());
        }

        return incomes;
    }

    private IncomeRequestDTO getRequestDTO() {
        IncomeRequestDTO requestDTO = new IncomeRequestDTO();
        requestDTO.setAmount(BigDecimal.TEN);
        requestDTO.setDescription("teste create");
        requestDTO.setReceiptDate(LocalDate.now());

        return requestDTO;
    }
}
package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.MonthlyBalance;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.ExpenseRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.MonthlyBalanceRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
class ExpenseServiceImplTest {

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Mock
    private ExpenseRepositoryImpl expenseRepository;

    @Mock
    private MonthlyBalanceRepositoryImpl monthlyBalanceRepository;

    @Test
    void findAll() {
        List<ExpenseDTO> expenseDTOS = getExpenseDtos();
        when(expenseRepository.findAll()).thenReturn(expenseDTOS);

        var response = expenseService.findAll();
        assertEquals(10, response.size());
        assertEquals("Conta de luz", response.get(0).getDescription());
    }

    @Test
    void findById() {
        ExpenseDTO expenseDTO = getExpenseDTO();
        UUID uuid = UUID.randomUUID();

        when(expenseRepository.findById(uuid)).thenReturn(expenseDTO);

        var response = expenseService.findById(uuid);
        assertEquals("Conta de luz", response.getDescription());
    }

    @Test
    void update() {
        ExpenseDTO expenseDTO = getExpenseDTO();
        expenseDTO.setExpenseStatus("PENDING");
        expenseDTO.setDescription("Conta de Agua");
        ExpenseRequestDTO expenseUpdatedDTO = getExpenseRequestDTO();
        UUID uuid = UUID.randomUUID();

        when(expenseRepository.update(expenseUpdatedDTO, uuid)).thenReturn(1);
        when(expenseRepository.findById(uuid)).thenReturn(expenseDTO);

        var response = expenseService.findById(uuid);
        assertNotEquals("Conta de luz", response.getDescription());
        assertNotEquals("LIQUIDATED", response.getExpenseStatus());
        assertEquals("Conta de Agua", response.getDescription());
    }

    @Test
    void create() {
        ExpenseRequestDTO requestDTO = getExpenseRequestDTO();
        ExpenseDTO expenseDTO = getExpenseDTO();
        MonthlyBalanceDTO monthlyBalance = getMonthlyBalance();

        when(expenseRepository.create(requestDTO)).thenReturn(1);
        when(expenseRepository.findById(any())).thenReturn(expenseDTO);
        when(monthlyBalanceRepository.getMonthlyBalance(any())).thenReturn(monthlyBalance);

        var response = expenseService.create(requestDTO);
        assertNotNull(response);
        assertEquals(LocalDate.of(2022, 05, 01), response.getMonthlyBalanceReferenceDate());
    }

    @Test
    void delete() {
        ExpenseDTO expenseDTO = getExpenseDTO();
        UUID uuid = UUID.randomUUID();

        when(expenseService.findById(any())).thenReturn(expenseDTO);

        expenseService.delete(uuid);

        verify(expenseRepository, times(1)).delete(uuid);

    }

    @Test
    void updateExpenseStatus() {
        ExpenseDTO expenseDTO = getExpenseDTO();
        UUID uuid = UUID.randomUUID();

        when(expenseService.findById(any())).thenReturn(expenseDTO);
        expenseService.updateExpenseStatus(ExpenseStatus.PENDING.name(), uuid);

        verify(expenseRepository, times(1)).updateStatus(ExpenseStatus.PENDING, uuid);
    }

    private List<ExpenseDTO> getExpenseDtos() {
        List<ExpenseDTO> expenseDTOS = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expenseDTOS.add(getExpenseDTO());
        }
        return expenseDTOS;
    }

    private ExpenseDTO getExpenseDTO() {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setExpenseStatus("LIQUIDATED");
        expenseDTO.setAmount(BigDecimal.TEN);
        expenseDTO.setDescription("Conta de luz");
        expenseDTO.setDueDate(LocalDate.now());
        expenseDTO.setRecurrent(false);
        expenseDTO.setMonthlyBalanceReferenceDate(LocalDate.of(2022, 05, 01));
        expenseDTO.setUuid(UUID.randomUUID());

        return expenseDTO;
    }

    private ExpenseRequestDTO getExpenseRequestDTO() {
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO();
        requestDTO.setExpenseStatus(ExpenseStatus.PENDING);
        requestDTO.setAmount(BigDecimal.TEN);
        requestDTO.setDescription("Conta de Agua");
        requestDTO.setDueDate(LocalDate.now());
        requestDTO.setRecurrent(false);
        requestDTO.setUuid(UUID.randomUUID());

        return requestDTO;
    }

    private MonthlyBalanceDTO getMonthlyBalance() {
        MonthlyBalanceDTO monthlyBalance = new MonthlyBalanceDTO();
        monthlyBalance.setUuid(UUID.randomUUID());
        monthlyBalance.setReferenceDate(LocalDate.of(2022, 05, 01));

        return monthlyBalance;
    }
}
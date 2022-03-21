package br.com.dev.valber.medeiros.controleficancas.repository.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ExpenseRepositoryImplTest {

    @Spy
    @InjectMocks
    private ExpenseRepositoryImpl expenseRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void findAll() {
        when(expenseRepository.findAll()).thenReturn(getExpenseDtos());
        var all = expenseRepository.findAll();
        assertNotNull(all);
    }

    @Test
    void findById() {
        UUID uuid = UUID.randomUUID();
        when(expenseRepository.findById(uuid)).thenReturn(getExpenseDTO());
        expenseRepository.findById(uuid);
        verify(expenseRepository, times(2)).findById(any());
    }

    @Test
    void create() {
        ExpenseRequestDTO requestDTO = getExpenseRequestDTO();
        when(expenseRepository.create(requestDTO)).thenReturn(1);
        var i = expenseRepository.create(requestDTO);
        assertEquals(1, i);
    }

    @Test
    void delete() {
        UUID uuid = UUID.randomUUID();
        String sql = "DELETE FROM expense WHERE uuid = ?";
        expenseRepository.delete(uuid);
        verify(jdbcTemplate, times(1)).update(sql, uuid);
    }

    @Test
    void update() {
        ExpenseRequestDTO requestDTO = getExpenseRequestDTO();
        UUID uuid = UUID.randomUUID();
        when(expenseRepository.update(requestDTO, uuid)).thenReturn(1);
        var update = expenseRepository.update(requestDTO, uuid);
        assertEquals(1, update);
    }

    @Test
    void updateStatus() {
        UUID uuid = UUID.randomUUID();
        ExpenseStatus expenseStatus = ExpenseStatus.PENDING;
        when(expenseRepository.updateStatus(expenseStatus, uuid)).thenReturn(1);
        var i = expenseRepository.updateStatus(expenseStatus, uuid);
        assertEquals(1, i);
    }

    @Test
    void getExpenseWithDueDebt() {
        when(expenseRepository.getExpenseWithDueDebt()).thenReturn(getExpenseDtos());
        var expenseWithDueDebt = expenseRepository.getExpenseWithDueDebt();
        assertNotNull(expenseWithDueDebt);
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
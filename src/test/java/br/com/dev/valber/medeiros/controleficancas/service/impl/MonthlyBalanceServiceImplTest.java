package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.*;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.MonthlyBalanceRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MonthlyBalanceServiceImplTest {

    @InjectMocks
    private MonthlyBalanceServiceImpl monthlyBalanceService;

    @Mock
    private MonthlyBalanceRepositoryImpl monthlyBalanceRepository;

    private String referenceDate;

    @BeforeEach
    void setUp() {
        referenceDate = "2022-05";
    }

    @Test
    void getMonthlyBalanceReferencesDate() {
        when(monthlyBalanceRepository.getMonthlyBalanceDateReferences()).thenReturn(getMonthlyBalances());
        List<MonthlyBalanceDateReferenceDTO> monthlyBalanceReferencesDate = monthlyBalanceService
                .getMonthlyBalanceReferencesDate();
        assertEquals(10, monthlyBalanceReferencesDate.size());
    }

    @Test
    void getExpensesForMonthlyBalances() {
        when(monthlyBalanceRepository.getExpensesForMonthlyBalances(any())).thenReturn(getExpenseDtos());
        List<ExpenseDTO> response = monthlyBalanceService
                .getExpensesForMonthlyBalances(referenceDate);
        assertEquals(10, response.size());
        assertEquals("2022-05-01", response.get(0).getMonthlyBalanceReferenceDate().toString());
    }

    @Test
    void getExpensesForMonthlyBalancesSholdExpensesEmptyWhenExpensesNotFound() {
        when(monthlyBalanceRepository.getExpensesForMonthlyBalances(any()))
                .thenThrow(EmptyResultDataAccessException.class);
        List<ExpenseDTO> expensesForMonthlyBalances = monthlyBalanceService
                .getExpensesForMonthlyBalances(referenceDate);
        assertTrue(expensesForMonthlyBalances.isEmpty());
    }

    @Test
    void getTotalBalanceExpense() {
        when(monthlyBalanceRepository.getTotalBalanceExpense(any())).thenReturn(getTotalBalanceDTO());
        TotalBalanceDTO totalBalanceExpense = monthlyBalanceService.getTotalBalanceExpense(referenceDate);
        assertEquals(BigDecimal.TEN, totalBalanceExpense.getTotalAmount());
    }

    @Test
    void getTotalBalanceExpenseSholdThrowBusinnesExceptionWhenTotalBalanceExpensesIsEmpty() {
        when(monthlyBalanceRepository.getTotalBalanceExpense(any())).thenThrow(EmptyResultDataAccessException.class);
        Exception exception = assertThrows(BusinessException.class, () -> {
           monthlyBalanceService.getTotalBalanceExpense(referenceDate);
        });
        assertEquals(String.format("Total balance expenses for reference date %s not found.", referenceDate),
                exception.getMessage());
    }

    @Test
    void create() {
        when(monthlyBalanceRepository.create(any())).thenReturn(1);
        when(monthlyBalanceRepository.getMonthlyBalance(any())).thenReturn(getMonthlyBalanceDTO());
        MonthlyBalanceDTO monthlyBalanceDTO = monthlyBalanceService.create(getMonthlyBalanceRequestDTO());
        assertEquals("2022-05-01", monthlyBalanceDTO.getReferenceDate().toString());
    }

    @Test
    void getIncomesForMonthlyBalance() {
        when(monthlyBalanceRepository.getIncomesForMonthlyBalance(any())).thenReturn(getIncomes());
        List<IncomeDTO> incomesForMonthlyBalance = monthlyBalanceService.getIncomesForMonthlyBalance(referenceDate);
        assertEquals(10, incomesForMonthlyBalance.size());
    }

    @Test
    void getIncomesForMonthlyBalanceSholdReturnEmptyListWhenIncomesNotFound() {
        when(monthlyBalanceRepository.getIncomesForMonthlyBalance(any())).thenThrow(EmptyResultDataAccessException.class);
        List<IncomeDTO> incomesForMonthlyBalance = monthlyBalanceService.getIncomesForMonthlyBalance(referenceDate);
        assertTrue(incomesForMonthlyBalance.isEmpty());
    }

    @Test
    void getTotalBalanceIncome() {
        when(monthlyBalanceRepository.getTotalBalanceIncomes(any())).thenReturn(getTotalBalanceDTO());
        TotalBalanceDTO totalBalanceIncome = monthlyBalanceService.getTotalBalanceIncome(referenceDate);
        assertEquals(BigDecimal.TEN, totalBalanceIncome.getTotalAmount());
    }

    @Test
    void getTotalBalanceIncomeSholdThrowBusinessExceptionWhenTotalIncomeNotFound() {
        when(monthlyBalanceRepository.getTotalBalanceIncomes(any())).thenThrow(EmptyResultDataAccessException.class);
        Exception exception = assertThrows(BusinessException.class, () -> {
            monthlyBalanceService.getTotalBalanceIncome(referenceDate);
        });
        assertEquals(String.format("Total balance incomes for reference date %s not found.", referenceDate),
                exception.getMessage());
    }

    private MonthlyBalanceDTO getMonthlyBalanceDTO() {
        MonthlyBalanceDTO monthlyBalanceDTO = new MonthlyBalanceDTO();
        monthlyBalanceDTO.setUuid(UUID.randomUUID());
        monthlyBalanceDTO.setReferenceDate(LocalDate.of(2022, 5, 1));
        monthlyBalanceDTO.setExpenses(new ArrayList<>());
        return monthlyBalanceDTO;
    }

    private List<MonthlyBalanceDateReferenceDTO> getMonthlyBalances() {
        List<MonthlyBalanceDateReferenceDTO> monthlyBalanceDateReferenceDTOS = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            monthlyBalanceDateReferenceDTOS.add(getMonthlyBalanceDateReferenceDTO());
        }
        return monthlyBalanceDateReferenceDTOS;
    }

    private MonthlyBalanceDateReferenceDTO getMonthlyBalanceDateReferenceDTO() {
        MonthlyBalanceDateReferenceDTO monthlyBalanceDateReferenceDTO = new MonthlyBalanceDateReferenceDTO();
        monthlyBalanceDateReferenceDTO.setUuid(UUID.randomUUID());
        monthlyBalanceDateReferenceDTO.setReferenceDate(LocalDate.now());
        return monthlyBalanceDateReferenceDTO;
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

    private TotalBalanceDTO getTotalBalanceDTO() {
        TotalBalanceDTO totalBalanceDTO = new TotalBalanceDTO();
        totalBalanceDTO.setTotalAmount(BigDecimal.TEN);
        totalBalanceDTO.setUuid(UUID.randomUUID());
        totalBalanceDTO.setReferenceDate(LocalDate.now());
        return totalBalanceDTO;
    }

    private MonthlyBalanceRequestDTO getMonthlyBalanceRequestDTO() {
        MonthlyBalanceRequestDTO requestDTO = new MonthlyBalanceRequestDTO();
        requestDTO.setReferenceDate(referenceDate);
        return requestDTO;
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
}
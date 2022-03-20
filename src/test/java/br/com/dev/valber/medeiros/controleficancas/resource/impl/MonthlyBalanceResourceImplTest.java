package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.*;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.service.impl.MonthlyBalanceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MonthlyBalanceResourceImplTest {
    public static final String PATH = "/api/balances";

    private MockMvc mockMvc;

    @InjectMocks
    private MonthlyBalanceResourceImpl resource;

    @Mock
    private MonthlyBalanceServiceImpl service;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    void getMonthlyBalanceDateReferences() throws Exception {
        var response = getMonthlyBalances();

        when(service.getMonthlyBalanceReferencesDate()).thenReturn(response);

        mockMvc.perform(
                get(PATH + "/references"))
                .andExpect(status().isOk());
    }

    @Test
    void getExpensesForMonthlyBalances() throws Exception {
        var response = getExpenseDtos();

        when(service.getExpensesForMonthlyBalances(any())).thenReturn(response);

        mockMvc.perform(
                get(PATH + "/references/expenses")
                        .param("reference", "2022-04-01")
                )
                .andExpect(status().isOk());
    }

    @Test
    void getTotalBalanceExpense() throws Exception {
        var response = getTotalBalanceDTO();

        when(service.getTotalBalanceExpense(any())).thenReturn(response);

        mockMvc.perform(
                get(PATH + "/references/balance/expense")
                        .param("reference", "2022-04-01")
        )
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var monthlyBalanceRequestDTO = getMonthlyBalanceRequestDTO();
        var content = mapper.writeValueAsString(monthlyBalanceRequestDTO);

        mockMvc.perform(
                post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(status().isCreated());
    }

    @Test
    void getIncomesForMonthlyBalances() throws Exception {
        var response = getIncomes();

        when(service.getIncomesForMonthlyBalance(any())).thenReturn(response);

        mockMvc.perform(
                get(PATH + "/references/incomes")
                        .param("reference", "2022-05-01")
        )
                .andExpect(status().isOk());
    }

    @Test
    void getTotalBalanceIncomes() throws Exception {
        var response = getTotalBalanceDTO();

        when(service.getTotalBalanceIncome(any())).thenReturn(response);

        mockMvc.perform(
                        get(PATH + "/references/balance/incomes")
                                .param("reference", "2022-04-01")
                )
                .andExpect(status().isOk());
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
        requestDTO.setReferenceDate("2022-05-01");
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
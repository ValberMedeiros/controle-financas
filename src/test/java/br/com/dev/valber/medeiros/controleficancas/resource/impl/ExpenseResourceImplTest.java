package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.service.impl.ExpenseServiceImpl;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
class ExpenseResourceImplTest {

    public static final String PATH = "/api/expenses";
    
    private MockMvc mockMvc;

    @InjectMocks
    private ExpenseResourceImpl resource;

    @Mock
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    void findAll() throws Exception {
        var expenseDtos = getExpenseDtos();

        when(expenseService.findAll()).thenReturn(expenseDtos);

        mockMvc
                .perform(get(PATH))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var content = mapper.writeValueAsString(getExpenseRequestDTO());

        when(expenseService.create(any())).thenReturn(getExpenseDTO());

        mockMvc
                .perform(post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var content = mapper.writeValueAsString(getExpenseRequestDTO());

        when(expenseService.update(any(), any())).thenReturn(getExpenseDTO());

        mockMvc
                .perform(put(PATH + "/{id}", UUID.randomUUID())
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

    }

    @Test
    void delete() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete(PATH + "/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findById() throws Exception {
        when(expenseService.findById(any())).thenReturn(getExpenseDTO());

        mockMvc
                .perform(get(PATH + "/{id}", UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    @Test
    void updateStatus() throws Exception {
        mockMvc
                .perform(put(PATH + "/{uuid}/status/{status}", UUID.randomUUID(), "PENDING"))
                .andExpect(status().isOk());
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
        requestDTO.setMonthlyBalanceUuid(UUID.randomUUID());

        return requestDTO;
    }
}
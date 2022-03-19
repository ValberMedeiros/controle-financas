package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.service.impl.IncomeServiceImpl;
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
class IncomeResourceImplTest {

    public static final String PATH = "/api/incomes";

    private MockMvc mockMvc;

    @InjectMocks
    private IncomeResourceImpl resource;

    @Mock
    private IncomeServiceImpl service;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    void findAll() throws Exception {
        var incomes = getIncomes();

        when(service.findAll()).thenReturn(incomes);

        mockMvc.perform(
                get(PATH))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var income = getIncome();
        var content = mapper.writeValueAsString(income);

        when(service.create(any())).thenReturn(income);

        mockMvc.perform(
                post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());

    }

    @Test
    void update() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var income = getIncome();
        var content = mapper.writeValueAsString(income);

        when(service.update(any(), any())).thenReturn(income);

        mockMvc.perform(
                put(PATH + "/{id}", UUID.randomUUID())
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(PATH + "/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
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
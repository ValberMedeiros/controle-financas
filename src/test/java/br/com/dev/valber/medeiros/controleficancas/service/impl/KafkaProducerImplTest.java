package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class KafkaProducerImplTest {

    @InjectMocks
    private KafkaProducerImpl kafkaProducer;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void sendMessage() {
        ExpenseDTO expenseDTO = getExpenseDTO();

        kafkaProducer.sendMessage(expenseDTO);

        verify(kafkaTemplate, times(1)).send(any(), any(), any());
    }

    @Test
    void sendMessageSholdThrowBusinessExceptionWhenFailMessageSend() {
        ExpenseDTO expenseDTO = getExpenseDTO();

        when(kafkaTemplate.send(any(), any(), any())).thenAnswer(invocationOnMock -> {
            throw new JsonMappingException("Ocorreu um erro, tente novamente mais tarde.");
        });

        Exception exception = assertThrows(BusinessException.class, () -> {
            kafkaProducer.sendMessage(expenseDTO);
        });

        String expectedMessage = String.format("Ocorreu um erro, tente novamente mais tarde.");
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

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
}
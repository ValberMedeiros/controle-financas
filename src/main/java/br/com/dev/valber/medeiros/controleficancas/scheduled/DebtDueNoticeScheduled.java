package br.com.dev.valber.medeiros.controleficancas.scheduled;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.ExpenseRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.impl.KafkaProducerImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class DebtDueNoticeScheduled {

    private final ExpenseRepositoryImpl expenseRepository;
    private final KafkaProducerImpl kafkaProducer;

    public DebtDueNoticeScheduled(ExpenseRepositoryImpl expenseRepository, KafkaProducerImpl kafkaProducer) {
        this.expenseRepository = expenseRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(cron = "0 00 09 * * *")
    void debtDueNoticeJob() {

        List<ExpenseDTO> expenses;
        expenses = expenseRepository.getExpenseWithDueDebt();

        expenses.forEach(kafkaProducer::sendMessage);

    }

}

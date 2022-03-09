package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.service.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static final String CONTROLE_FINANCAS_TOPIC_NAME = "controle.financas.topic";


    @Override
    public void sendMessage(ExpenseDTO msg) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Thread.currentThread().setContextClassLoader(null);
        String key = UUID.randomUUID().toString();
        try {
            kafkaTemplate.send(CONTROLE_FINANCAS_TOPIC_NAME, key, mapper.writeValueAsString(msg));
        } catch (JsonProcessingException e) {
            throw new BusinessException("Ocorreu um erro, tente novamente mais tarde.", "generic.error");
        }
        kafkaTemplate.flush();
    }
}

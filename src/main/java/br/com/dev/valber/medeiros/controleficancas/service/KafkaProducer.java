package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;

public interface KafkaProducer {

    public void sendMessage(ExpenseDTO msg);

}

package br.com.dev.valber.medeiros.controleficancas.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BusinessException extends RuntimeException{

    private final String informationCode;
    private final UUID uniqueId;

    public BusinessException(String message, String informationCode) {
        super(message);
        this.informationCode = informationCode;
        uniqueId = UUID.randomUUID();
    }
}

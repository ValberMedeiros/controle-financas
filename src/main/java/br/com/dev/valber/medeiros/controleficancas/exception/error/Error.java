package br.com.dev.valber.medeiros.controleficancas.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String uniqueId;
    private String informationCode;
    private String message;
}

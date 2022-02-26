package br.com.dev.valber.medeiros.controleficancas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -456743942912351847L;

    private UUID uuid;
    private LocalDate receiptDate;
    private String description;
    private BigDecimal amount;
}

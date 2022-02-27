package br.com.dev.valber.medeiros.controleficancas.domain.dto;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "Código unico da renda", dataType = "uuid")
    private UUID uuid;

    @ApiModelProperty(notes = "Data de recebimento da renda", example = "2022-03-15")
    private LocalDate receiptDate;

    @ApiModelProperty(notes = "Descrição da renda", example = "Aluguel")
    private String description;

    @ApiModelProperty(notes = "Valor da renda", example = "890.60")
    private BigDecimal amount;
}

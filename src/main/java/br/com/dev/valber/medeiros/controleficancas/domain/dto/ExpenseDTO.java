package br.com.dev.valber.medeiros.controleficancas.domain.dto;

import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2428276050165637651L;

    @ApiModelProperty(notes = "Status do pagamento da despesa")
    private ExpenseStatus expenseStatus;

    @ApiModelProperty(notes = "Informação de despesa recorrente")
    private Boolean recurrent;

    @ApiModelProperty(notes = "Data de vencimento da despesa")
    private LocalDate dueDate;

    @ApiModelProperty(notes = "UUID do balanço referente a despesa")
    private UUID monthlyBalanceUuid;

    @ApiModelProperty(notes = "Descrição da despesa")
    private String description;

    @ApiModelProperty(notes = "Valor da despesa")
    private BigDecimal amount;

}

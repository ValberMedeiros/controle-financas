package br.com.dev.valber.medeiros.controleficancas.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2428276050165637651L;

    @ApiModelProperty(notes = "Status do pagamento da despesa")
    private String expenseStatus;

    @ApiModelProperty(notes = "Informação de despesa recorrente")
    private Boolean recurrent;

    @ApiModelProperty(notes = "Data de vencimento da despesa")
    private LocalDate dueDate;

    @JsonFormat(pattern = "yyyy-MM")
    @ApiModelProperty(notes = "UUID do balanço referente a despesa")
    private LocalDate monthlyBalanceReferenceDate;

    @ApiModelProperty(notes = "Descrição da despesa")
    private String description;

    @ApiModelProperty(notes = "Valor da despesa")
    private BigDecimal amount;

}

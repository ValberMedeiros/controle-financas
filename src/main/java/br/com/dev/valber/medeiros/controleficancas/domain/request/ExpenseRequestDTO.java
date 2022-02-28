package br.com.dev.valber.medeiros.controleficancas.domain.request;

import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ExpenseRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4512362624138956235L;

    @JsonIgnore
    private UUID uuid;

    @ApiModelProperty(required = true, notes = "Status do pagamento da despesa")
    private ExpenseStatus expenseStatus;

    @ApiModelProperty(required = true, notes = "Informação de despesa recorrente")
    private Boolean recurrent;

    @ApiModelProperty(required = true, notes = "Data de vencimento da despesa")
    private LocalDate dueDate;

    @ApiModelProperty(required = true, notes = "UUID do balanço referente a despesa")
    private UUID monthlyBalanceUuid;

    @ApiModelProperty(required = true, notes = "Descrição da despesa")
    private String description;

    @ApiModelProperty(required = true, notes = "Valor da despesa")
    private BigDecimal amount;

}

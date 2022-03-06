package br.com.dev.valber.medeiros.controleficancas.domain.request;

import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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

    @NotNull
    @ApiModelProperty(required = true, notes = "Status do pagamento da despesa")
    private ExpenseStatus expenseStatus;

    @NotNull
    @ApiModelProperty(required = true, notes = "Informação de despesa recorrente")
    private Boolean recurrent;

    @NotNull
    @ApiModelProperty(required = true, notes = "Data de vencimento da despesa")
    private LocalDate dueDate;

    @NotNull
    @ApiModelProperty(required = true, notes = "UUID do balanço referente a despesa")
    private UUID monthlyBalanceUuid;

    @NotBlank
    @NotEmpty
    @ApiModelProperty(required = true, notes = "Descrição da despesa")
    private String description;

    @PositiveOrZero
    @ApiModelProperty(required = true, notes = "Valor da despesa")
    private BigDecimal amount;

}

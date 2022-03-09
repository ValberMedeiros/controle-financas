package br.com.dev.valber.medeiros.controleficancas.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2428276050165637651L;

    @ApiModelProperty(notes = "UUID da despesa")
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Status do pagamento da despesa")
    private String expenseStatus;

    @ApiModelProperty(notes = "Informação de despesa recorrente")
    private Boolean recurrent;

    @ApiModelProperty(notes = "Data de vencimento da despesa")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd ")
    private LocalDate dueDate;

    @JsonFormat(pattern = "yyyy-MM")
    @ApiModelProperty(notes = "Ano/MÊs do balanço referente a despesa")
    private LocalDate monthlyBalanceReferenceDate;

    @ApiModelProperty(notes = "Descrição da despesa")
    private String description;

    @ApiModelProperty(notes = "Valor da despesa")
    private BigDecimal amount;

    public ExpenseDTO(String expenseStatus, Boolean recurrent, LocalDate dueDate, LocalDate monthlyBalanceReferenceDate, String description, BigDecimal amount) {
        this.expenseStatus = expenseStatus;
        this.recurrent = recurrent;
        this.dueDate = dueDate;
        this.monthlyBalanceReferenceDate = monthlyBalanceReferenceDate;
        this.description = description;
        this.amount = amount;
    }
}

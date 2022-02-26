package br.com.dev.valber.medeiros.controleficancas.domain;

import br.com.dev.valber.medeiros.controleficancas.domain.enums.ExpenseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense extends Entry {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo expenseStatus é obrigatório")
    private ExpenseStatus expenseStatus;

    @NotNull(message = "O campo recurrent é obrigatório")
    private Boolean recurrent;

    @NotNull(message = "O campo dueDate é obrigatório")
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "monthly_balance_uuid")
    private MonthlyBalance monthlyBalance;

}

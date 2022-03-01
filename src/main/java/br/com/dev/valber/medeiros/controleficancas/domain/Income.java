package br.com.dev.valber.medeiros.controleficancas.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Income extends Entry {

    @NotNull(message = "O campo receiptDate é obrigatório")
    private LocalDate receiptDate;

    @ManyToOne
    @JoinColumn(name = "monthly_balance_uuid")
    private MonthlyBalance monthlyBalance;

}

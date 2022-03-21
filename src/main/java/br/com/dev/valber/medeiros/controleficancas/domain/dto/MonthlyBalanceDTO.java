package br.com.dev.valber.medeiros.controleficancas.domain.dto;

import br.com.dev.valber.medeiros.controleficancas.domain.Expense;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBalanceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4389853177611650498L;

    @ApiModelProperty(notes = "Código unico do balanço mensal", dataType = "uuid")
    private UUID uuid;

    @ApiModelProperty(value = "Ano/Mês de referência do balanço de dívidas do mês")
    @JsonFormat(pattern = "yyyy-MM")
    private LocalDate referenceDate;

    @ApiModelProperty(value = "Dívidas cadastradas no mês")
    @OneToMany(mappedBy = "monthlyBalance", orphanRemoval = true)
    private transient List<Expense> expenses = new ArrayList<>();

}

package br.com.dev.valber.medeiros.controleficancas.domain.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBalanceRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5181227238073301439L;

    @JsonIgnore
    @ApiModelProperty(value = "UUID do Ano/Mês de referência do balanço de dívidas do mês")
    private UUID uuid;

    @ApiModelProperty(value = "Ano/Mês de referência do balanço de dívidas do mês")
    private String referenceDate;

}

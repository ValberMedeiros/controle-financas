package br.com.dev.valber.medeiros.controleficancas.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBalanceDateReferenceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7391065944134590468L;

    @ApiModelProperty(value = "UUID do balanço de dívidas do mês")
    private UUID uuid;

    @ApiModelProperty(value = "Ano/Mês de referência do balanço de dívidas do mês")
    @JsonFormat(pattern = "yyyy-MM")
    private LocalDate referenceDate;
}

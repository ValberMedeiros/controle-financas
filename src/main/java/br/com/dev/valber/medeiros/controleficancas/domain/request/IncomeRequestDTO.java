package br.com.dev.valber.medeiros.controleficancas.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class IncomeRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 536260004072190785L;

    @JsonIgnore
    private UUID uuid;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(required = true, notes = "Data de recebimento da renda", example = "2022-03-15")
    private LocalDate receiptDate;

    @NotBlank
    @NotEmpty
    @ApiModelProperty(required = true, notes = "Descrição da renda", example = "Aluguel")
    private String description;

    @PositiveOrZero
    @ApiModelProperty(required = true, notes = "Valor da renda", example = "890.60")
    private BigDecimal amount;
}

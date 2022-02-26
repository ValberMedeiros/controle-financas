package br.com.dev.valber.medeiros.controleficancas.domain.response;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6563526884495586268L;

    private List<IncomeDTO> data;
}

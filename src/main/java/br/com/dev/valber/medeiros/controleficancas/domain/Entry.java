package br.com.dev.valber.medeiros.controleficancas.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Entry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1814419919048264463L;

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "UUIDGenerator")
    private UUID uuid;

    @NotBlank(message = "O campo description é obrigatório")
    private String description;

    @NotNull(message = "O campo amount é obrigatório")

    private BigDecimal amount;

}

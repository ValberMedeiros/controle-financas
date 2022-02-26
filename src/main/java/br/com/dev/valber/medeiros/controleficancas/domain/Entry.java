package br.com.dev.valber.medeiros.controleficancas.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Entry {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "UUIDGenerator")
    private UUID uuid;

    @NotBlank(message = "O campo description é obrigatório")
    private String description;

    @NotNull(message = "O campo amount é obrigatório")
    private BigDecimal amount;

}

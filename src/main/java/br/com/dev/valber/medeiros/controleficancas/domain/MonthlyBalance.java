package br.com.dev.valber.medeiros.controleficancas.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyBalance implements Serializable {

    @Serial
    private static final long serialVersionUID = -2785640014396236820L;

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID uuid;

    @JsonFormat(pattern = "yyyy-MM")
    private LocalDate referenceDate;

    @OneToMany(mappedBy = "monthlyBalance", orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

}

package br.com.dev.valber.medeiros.controleficancas.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UUser")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -5762093156660230614L;

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID uuid;

    @NotEmpty
    @NotBlank
    @Email(message = "Usuário inválido, infome um email válido")
    @Column(unique = true)
    private String login;


    @NotBlank
    @NotEmpty
    @NotNull
    private String password;

}

package br.com.dev.valber.medeiros.controleficancas.exception.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DefaultError {

    private String timestamp;
    private Integer status;
    private String path;
    private List<Error> errors = new ArrayList<>();

    public DefaultError(HttpStatus status, String path, List<Error> errors) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status.value();
        this.path = path;
        this.errors = errors;
    }
}

package br.com.dev.valber.medeiros.controleficancas.resource.exception;

import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.exception.error.*;
import br.com.dev.valber.medeiros.controleficancas.exception.error.Error;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StandardError> entityNotFoundHandler(BusinessException ex, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var error = getStandardError(status, request, ex.getMessage(), ex.getInformationCode());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        String message = handleInvalidFormat((InvalidFormatException) ex.getCause());
        var error = getStandardError(status, request, message, "validation.exception");
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> userNotFoundHandler(UsernameNotFoundException ex, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var error = getStandardError(status, request, ex.getMessage(), "user.not.found");
        return ResponseEntity.status(status).body(error);
    }

    private String handleInvalidFormat(InvalidFormatException ex) {

        String path = joinPath(ex.getPath());

        return String.format("A propriedade '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private StandardError getStandardError(HttpStatus status,
                                           HttpServletRequest request, String errorMsg, String informationCode) {
        var errors = new ArrayList<Error>();
        var error = new Error();
        error.setMessage(errorMsg);
        error.setInformationCode(informationCode);
        error.setUniqueId(UUID.randomUUID());

        errors.add(error);

        var standardError = new StandardError();
        standardError.setTimestamp(LocalDateTime.now());
        standardError.setStatus(status.value());
        standardError.setPath(request.getRequestURI());
        standardError.setErrors(errors);
        return standardError;
    }
}

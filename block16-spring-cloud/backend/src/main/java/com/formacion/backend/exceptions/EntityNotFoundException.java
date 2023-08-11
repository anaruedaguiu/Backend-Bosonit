package com.formacion.backend.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Data
public class EntityNotFoundException extends Exception {
    private Date timestamp;
    private int httpCode;
    public EntityNotFoundException(String message, int codigo) {
        super(message);
        setHttpCode(codigo);
        setTimestamp(new Date());
    }
}

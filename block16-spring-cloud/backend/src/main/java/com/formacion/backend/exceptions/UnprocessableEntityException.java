package com.formacion.backend.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@Data
public class UnprocessableEntityException extends Exception {
    private Date timestamp;
    private int httpCode;
    public UnprocessableEntityException(String message, int codigo) {
        super(message);
        setHttpCode(codigo);
        setTimestamp(new Date());
    }
}

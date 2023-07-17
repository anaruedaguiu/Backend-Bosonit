package com.formacion.block7crudvalidation.exceptions;

import com.formacion.block7crudvalidation.domain.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {
    private Date timestamp = new Date();
    public UnprocessableEntityException() {
    }
    public CustomError getUnprocessableEntityException() {
        return new CustomError(timestamp, HttpStatus.UNPROCESSABLE_ENTITY.value(), "UNPROCESSABLE ENTITY: la validación de los campos no cumple los requisitos establecidos");
    }
}

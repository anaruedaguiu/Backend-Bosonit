package com.formacion.block12mongodb.exceptions;

import com.formacion.block12mongodb.domain.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {
    private Date timestamp = new Date();
    public UnprocessableEntityException() {
    }
    public CustomError getUnprocessableEntityException() {
        return new CustomError(timestamp, HttpStatus.UNPROCESSABLE_ENTITY.value(), "UNPROCESSABLE ENTITY: El campo username es obligatorio");
    }
}
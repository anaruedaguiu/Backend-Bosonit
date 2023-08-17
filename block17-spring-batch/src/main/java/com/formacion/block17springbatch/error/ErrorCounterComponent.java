package com.formacion.block17springbatch.error;

import org.springframework.stereotype.Component;

@Component
public class ErrorCounterComponent {
    private ErrorCounter errorCounter = new ErrorCounter();

    public ErrorCounter getErrorCounter() {
        return errorCounter;
    }
}

package com.formacion.block11uploaddownloadfiles.exception;

import com.formacion.block11uploaddownloadfiles.application.StorageService;

public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

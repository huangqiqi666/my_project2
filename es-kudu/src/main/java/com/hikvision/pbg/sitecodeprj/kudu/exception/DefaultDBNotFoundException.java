package com.hikvision.pbg.sitecodeprj.kudu.exception;

public class DefaultDBNotFoundException extends RuntimeException {
    public DefaultDBNotFoundException() {
        super();
    }

    public DefaultDBNotFoundException(String message) {
        super(message);
    }
}

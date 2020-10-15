package com.zupbank.bank.infrastructure.service.email;

public class EmailException extends RuntimeException {

    private static final long serialVersionID = 1L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}

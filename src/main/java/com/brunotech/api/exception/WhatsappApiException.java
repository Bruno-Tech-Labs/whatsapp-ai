package com.brunotech.api.exception;

public class WhatsappApiException extends RuntimeException {

    public WhatsappApiException(String message) {
        super(message);
    }

    public WhatsappApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
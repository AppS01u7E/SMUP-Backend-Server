package com.appsolute.soomapi.infra.exception;

public class MailSendingException extends RuntimeException {
    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}

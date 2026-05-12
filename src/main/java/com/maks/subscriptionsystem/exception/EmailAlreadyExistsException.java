package com.maks.subscriptionsystem.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) { super(message); }
}

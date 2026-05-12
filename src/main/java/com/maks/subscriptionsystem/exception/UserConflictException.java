package com.maks.subscriptionsystem.exception;

public class UserConflictException extends RuntimeException {
    public UserConflictException (String message) {
        super(message);
    }
}

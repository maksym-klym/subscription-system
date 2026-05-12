package com.maks.subscriptionsystem.exception.handler;

import com.maks.subscriptionsystem.dto.ErrorDto;
import com.maks.subscriptionsystem.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(ItemNotFoundException exception) {
        return ErrorDto.of(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors())
            errors.put(error.getField(), error.getDefaultMessage());

        return ErrorDto.of(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto EmailAlreadyExistsException(EmailAlreadyExistsException exception) {
        return ErrorDto.of(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(InvoiceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto InvoiceConflictException(InvoiceConflictException exception) {
        return ErrorDto.of(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(SubscriptionConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto SubscriptionConflictException(SubscriptionConflictException exception) {
        return ErrorDto.of(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(UserConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto UserConflictException(UserConflictException exception) {
        return ErrorDto.of(HttpStatus.CONFLICT, exception);
    }
}

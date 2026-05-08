package com.maks.subscriptionsystem.exception.handler;

import com.maks.subscriptionsystem.dto.ErrorDto;
import com.maks.subscriptionsystem.exception.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(ItemNotFoundException exception) { return ErrorDto.of(HttpStatus.NOT_FOUND, exception); }
}

package com.maks.subscriptionsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDto {
    private LocalDateTime localDateTime;
    private int status;
    private String message;

    public ErrorDto(int status, String message) {
        this.localDateTime = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public static ErrorDto of(HttpStatus httpStatus, Exception exception) {
        String exceptionMessage = exception.getMessage();
        return new ErrorDto(httpStatus.value(),
                exceptionMessage == null || exceptionMessage.isBlank()
                        ? httpStatus.getReasonPhrase()
                        : exceptionMessage);
    }

    public static ErrorDto of(HttpStatus httpStatus, String message) {
        return new ErrorDto(httpStatus.value(), message);
    }
}

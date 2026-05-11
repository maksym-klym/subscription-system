package com.maks.subscriptionsystem.exception;

public class InvoiceAlreadyPaidException extends RuntimeException {
    public InvoiceAlreadyPaidException(Long id) {
        super("Invoice with ID " + id + " is already paid");
    }
}
